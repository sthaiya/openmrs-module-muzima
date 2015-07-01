function ErrorCtrl($scope, $routeParams, $location, $data) {
    // page parameter
    $scope.uuid = $routeParams.uuid;

    // get the current notification
    $data.getError($scope.uuid).
        then(function (response) {
            $scope.error = response.data;
            if (response.data.discriminator != "registration") {
                document.getElementById('editData').style.display = 'none';
            } else
                document.getElementById('editData').style.display = 'inline';
        }).then(function(){
            $scope.bindData();
            $scope.setEvent();
            $scope.setControls();
        });

    $scope.bindData = function(){
        $scope.ul_li_Data = '';
        $scope.ul_li_xml_Data = '';
        $scope.string_xml_Data = '';
        if($scope.error.discriminator =='xml-registration'){
            var jsonFormData = $scope.error.payload;
            $scope.to_ul_from_xml(jsonFormData,'treeul');
            $scope.xml_to_string(jsonFormData);
        }else{
            var jsonFormData = JSON.parse($scope.error.payload);
            $scope.to_ul(jsonFormData,'treeul');
            $scope.json_to_string(jsonFormData);
        }
        $scope.ul_li_Data = '';
        var jsonFormDataError = JSON.parse('{"Errors":'+$scope.error.Errors+'}');
        $scope.to_ul(jsonFormDataError,'treeError');

    };

    $scope.to_ul_from_xml = function(xml, htmlElement){
       $(xml).contents().each(function (i, node)
       {
           if( $(this).children().length>0){

              $scope.ul_li_xml_Data =  $scope.ul_li_xml_Data + "<li><span><i class = 'icon-minus-sign'></i>"+ this.tagName ;
              $scope.ul_li_xml_Data =  $scope.ul_li_xml_Data + "<ul>";
              $scope.to_ul_from_xml($(this));
              $scope.ul_li_xml_Data =  $scope.ul_li_xml_Data + "</ul>";
           }
           else{
                $scope.ul_li_xml_Data =  $scope.ul_li_xml_Data + "<li><span>"+ this.tagName ;
                $scope.ul_li_xml_Data =  $scope.ul_li_xml_Data + " : <b>"+ this.innerText+"</b></span></li>";
           }
       });
       $('#'+htmlElement).empty();
       $('#'+htmlElement).append($scope.ul_li_xml_Data);
     };

    $scope.to_ul = function(branches, htmlElement) {
         $.each(branches, function(key,value) {
              if(":"+value ==':[object Object]'){
                $scope.ul_li_Data = $scope.ul_li_Data + "<li><span><i class = 'icon-minus-sign'></i>&nbsp;"+ key ;
                $scope.ul_li_Data = $scope.ul_li_Data + "<ul>";
                $scope.to_ul(value, htmlElement);
                $scope.ul_li_Data = $scope.ul_li_Data + "</ul>";
              }
              else{
                $scope.ul_li_Data = $scope.ul_li_Data + "<li><span>"+ key ;
                $scope.ul_li_Data = $scope.ul_li_Data + " : <b>"+ value+"</b></span></li>";
                // console.log(key + ":"+ value);
              }
            });
         $('#'+htmlElement).empty();
         $('#'+htmlElement).append($scope.ul_li_Data);
      };

    $scope.json_to_string = function(branches) {
      	var strData =  JSON.stringify(branches)
    	strData = strData.replace(/\:{/g, ':\n\t\t{\n\t\t\t');
    	strData = strData.replace(/\","/g, '",\n\t\t\t"');
    	strData = strData.replace(/\},/g, '\n\t\t},\n');
    	$('#editJson').val(strData);
      };

    $scope.xml_to_string = function(branches) {
        strData = branches;
        strData = strData.replace(/\></g, '>\n<');
        $('#editJson').val(strData);
     };

    $scope.setControls = function(){
      	$('#editJsonSection').hide();
      	$( "#btnUpdate" ).prop( "disabled", true );
        $('#wait').hide();
        $('.messages').hide();
      }

    $scope.setEvent = function(){
      $('.icon-plus-sign, .icon-minus-sign').click(function(){
        $(this).parent().parent().find( "ul" ).toggle();
        if($(this).hasClass('icon-plus-sign')){

            $(this).removeClass( "icon-plus-sign" ).addClass( "icon-minus-sign" );

          }else if($(this).hasClass('icon-minus-sign')){

             $(this).removeClass( "icon-minus-sign" ).addClass( "icon-plus-sign" );
          }
      });

      $('.icon-plus-sign, .icon-minus-sign').hover(function(){
            $(this).css('cursor','hand');
       });

      $('#btnCancel').click(function(){
        $('#editJsonSection').hide();
        $( "#btnQueue" ).prop( "disabled", false );
        $( "#btnCancelQueue" ).prop( "disabled", false );
        $('.messages').hide();
      });

      $('.icon-edit').click(function(){
        $('#editJsonSection').show();
        $( "#btnQueue" ).prop( "disabled", true );
        $( "#btnCancelQueue" ).prop( "disabled", true );
        if($scope.error.discriminator =='xml-registration'){
            var jsonFormData = $scope.error.payload;
            $scope.xml_to_string(jsonFormData);
        }else{
            var jsonFormData = JSON.parse($scope.error.payload);
            $scope.json_to_string(jsonFormData);
        }
      });
      };

      $('#btnValidate').click(function(){
            $('#wait').show();
            $('.messages').hide();
            var jsonDataToValidate = $('#editJson').val();
            console.log(jsonDataToValidate);
            $data.validateData($scope.uuid,jsonDataToValidate).
            then(function (result) {
                $scope.ul_li_Data = '';
                $scope.to_ul(result.data,'treeError');
                //IF THERE ARE NO VALIDATION ERRORS THEN ENABLE UPDATE BUTTON
                if(Object.keys(result.data.Errors).length == 0){
                    $( "#btnUpdate" ).prop( "disabled", false );
                    $scope.isValid = true;
                }
                else{
                    $scope.isValid = false;
                    $('html,body').animate({scrollTop: $('#errorList').offset().top},1000);
                }
                $('.messages').show();
                $('#wait').hide();
            });
      });

      $('#btnUpdate').click(function(){

      });

      $('#btnQueue').click(function(){
       var uuidList = [$scope.uuid];
               $data.reQueueErrors(uuidList).
                   then(function () {
                       $location.path("/queues");
                   })
    });

    $( "#btnCancelQueue" ).click(function(){
       $location.path("/errors");
    });

    $scope.queue = function () {
        var uuidList = [$scope.uuid];
        alert("uuidList: " + uuidList);
        $data.reQueueErrors(uuidList).
            then(function () {
                $location.path("/errors");
            })
    };

    $scope.cancel = function () {
        $location.path('/errors');
    };

    $('#btnYes').click(function(){
      //SAVE THE EDITED DATA
      $('#wait').show();
      var formDataToSave = $('#editJson').val();
      console.log(formDataToSave);
      $data.saveEditedFormData($scope.uuid,formDataToSave).
      then(function (response) {
          $scope.error = response.data;
          $scope.bindData();
          $('#editJsonSection').hide();
          $( "#btnQueue" ).prop( "disabled", false );
          $( "#btnCancelQueue" ).prop( "disabled", false );
          $('.messages').hide();
          $('#wait').hide();
          $('#myModal').modal('hide');
      });
    });
    $('#btnNo').click(function(){

    });
}

function ErrorsCtrl($scope, $location, $data) {
    // initialize selected error data for re-queueing
    $scope.selected = {};
    // initialize the paging structure
    $scope.maxSize = 10;
    $scope.pageSize = 10;
    $scope.currentPage = 1;
    $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.errors = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.queue = function () {
        var uuidList = [];
        angular.forEach($scope.selected, function (value, key) {
            if (value) {
                uuidList.push(key);
            }
        });
        $data.reQueueErrors(uuidList).
            then(function () {
                $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
                    then(function (response) {
                        var serverData = response.data;
                        $scope.errors = serverData.objects;
                        $scope.noOfPages = serverData.pages;
                    });
            })
    };

    $scope.$watch('currentPage', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.errors = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);

    $scope.$watch('search', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.currentPage = 1;
            $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.errors = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);
}

function QueueCtrl($scope, $routeParams, $location, $data) {
    // page parameter
    $scope.uuid = $routeParams.uuid;
    // get the current notification
    $data.getQueue($scope.uuid).
        then(function (response) {
            $scope.queue = response.data;
        });

    $scope.delete = function () {
        var uuidList = [$scope.uuid];
        $data.deleteQueue(uuidList).
            then(function () {
                $location.path("/queues");
            })
    };

    $scope.cancel = function () {
        $location.path('/queues');
    };
}

function QueuesCtrl($scope, $location, $data) {
    // initialize selected error data for re-queueing
    $scope.selected = {};
    // initialize the paging structure
    $scope.maxSize = 10;
    $scope.pageSize = 10;
    $scope.currentPage = 1;
    $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.queues = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.delete = function () {
        var uuidList = [];
        angular.forEach($scope.selected, function (value, key) {
            if (value) {
                uuidList.push(key);
            }
        });
        $data.deleteQueue(uuidList).
            then(function () {
                $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
                    then(function (response) {
                        var serverData = response.data;
                        $scope.queues = serverData.objects;
                        $scope.noOfPages = serverData.pages;
                    });
            })
    };

    $scope.$watch('currentPage', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.queues = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);

    $scope.$watch('search', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.currentPage = 1;
            $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.queues = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);
}

function SourceCtrl($scope, $routeParams, $location, $data) {
    // initialize the source object
    $scope.source = {};
    // initialize the view to be read only
    $scope.mode = "view";
    $scope.uuid = $routeParams.uuid;
    if ($scope.uuid === undefined) {
        $scope.mode = "edit";
    } else {
        $data.getSource($scope.uuid).
            then(function (response) {
                $scope.source = response.data;
            });
    }

    $scope.edit = function () {
        $scope.mode = "edit";
    };

    $scope.cancel = function () {
        if ($scope.mode == "edit") {
            if ($scope.uuid === undefined) {
                $location.path("/sources");
            } else {
                $scope.mode = "view"
            }
        } else {
            $location.path("/sources");
        }
    };

    $scope.save = function (source) {
        $data.saveSource(source.uuid, source.name, source.description).
            then(function () {
                $location.path("/sources");
            })
    };

    $scope.delete = function () {
        $data.deleteSource($scope.uuid).
            then(function () {
                $location.path("/sources");
            })
    };
}

function SourcesCtrl($scope, $data) {
    // initialize the paging structure
    $scope.maxSize = 10;
    $scope.pageSize = 10;
    $scope.currentPage = 1;
    $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.sources = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.$watch('currentPage', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.sources = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);

    $scope.$watch('search', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.currentPage = 1;
            $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.sources = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);
}
function EditCtrl($scope, $routeParams, $location, $data) {
    // page parameter
    $scope.uuid = $routeParams.uuid;

    // get the current notification
    $data.getEdit($scope.uuid).
        then(function (response) {
            $scope.error = response.data;
            $scope.family_name = response.data.family_name;
            $scope.middle_name = response.data.middle_name;
            $scope.given_name = response.data.given_name;
            $scope.sex = response.data.sex;
            $scope.birth_date = response.data.birth_date;
            $scope.birthdate_estimated = response.data.birthdate_estimated;
            $scope.phone_number = response.data.phone_number;
            $scope.person_attribute4 = response.data.person_attribute4;
            $scope.uuid = response.data.patient_uuid;
            $scope.identifier_type_id = response.data.identifier_type_id;
            $scope.medical_record_number = response.data.medical_record_number;
            $scope.location_id = response.data.location_id;
            $scope.provider_id = response.data.provider_id;
            $scope.encounter_datetime = response.data.encounter_datetime;
            $scope.record_uuid = response.data.uuid;
            $scope.other_identifier_type = response.data.other_identifier_type;
            $scope.other_identifier_type_name = response.data.other_identifier_type_name;
            $scope.other_identifier_type_value = response.data.other_identifier_type_value;
        });


    $scope.postEditErrors = function (isValid) {

        var formData = JSON.stringify({
            family_name: $scope.family_name,
            middle_name: $scope.middle_name,
            given_name: $scope.given_name,
            sex: $scope.sex,
            patient_uuid: $scope.uuid,
            identifier_type_id: $scope.identifier_type_id,
            medical_record_number: $scope.medical_record_number,
            birth_date: $scope.birth_date,
            location_id: $scope.location_id,
            provider_id: $scope.provider_id,
            encounter_datetime: $scope.encounter_datetime,
            record_uuid: $scope.record_uuid,
            birthdate_estimated: $scope.birthdate_estimated,
            phone_number: $scope.phone_number,
            person_attribute4: $scope.person_attribute4,
            other_identifier_type: $scope.other_identifier_type,
            other_identifier_type_name: $scope.other_identifier_type_name,
            other_identifier_type_value: $scope.other_identifier_type_value
        });



        if(!checkDigit($scope.other_identifier_type_value)){
            document.getElementById('amrsError').innerHTML="";
            document.getElementById('identifierError').innerHTML="Please enter digits that matches CheckDigit algorithm";
        } else if(!checkDigit($scope.medical_record_number)){
            document.getElementById('identifierError').innerHTML="";
            document.getElementById('amrsError').innerHTML="Please enter digits that matches CheckDigit algorithm";
        } else if (isValid ){
            document.getElementById('identifierError').innerHTML="";
            document.getElementById('amrsError').innerHTML="";
            $data.editErrors(formData).
                then(function () {
                    $location.path("/errors");
                })
        }
    };

    $scope.edit = function () {
        var uuidList = [$scope.uuid];
        $data.reQueueErrors(uuidList).
            then(function () {
                $location.path("/edit");
            })
    };
    $scope.cancel = function () {
        $location.path('/errors');
    };
}

function checkDigit(number){

    var num = number.split('-');
    if (num.length != 2) {
        return false;
    }
    alert(luhnCheckDigit(num[0]));
    alert(num[1]);
    return luhnCheckDigit(num[0]) == num[1];
}

//
//check Digit Algorithm
//Source https://wiki.openmrs.org/display/docs/Check+Digit+Algorithm
function luhnCheckDigit(number) {
    var validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_";
    number = number.toUpperCase().trim();
    var sum = 0;
    for (var i = 0; i < number.length; i++) {
        var ch = number.charAt(number.length - i - 1);
        if (validChars.indexOf(ch) < 0) {
            return false;
        }
        var digit = ch.charCodeAt(0) - 48;
        var weight;
        if (i % 2 == 0) {
            weight = (2 * digit) - parseInt(digit / 5) * 9;
        }
        else {
            weight = digit;
        }
        sum += weight;
    }
    sum = Math.abs(sum) + 10;
    var digit = (10 - (sum % 10)) % 10;
    return digit;
}