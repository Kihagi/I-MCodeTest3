/**
 * Theme: Uplon Admin Template
 * Author: Coderthemes
 * Form wizard page
 */

!function ($) {
    "use strict";

    var FormWizard = function () {
    };

    FormWizard.prototype.createBasic = function ($form_container) {
        $form_container.children("div").steps({
            headerTag: "h3",
            bodyTag: "section",
            transitionEffect: "slideLeft",
            onFinishing: function (event, currentIndex) {
                //NOTE: Here you can do form validation and return true or false based on your validation logic
                console.log("Form has been validated!");
                return true;
            },
            onFinished: function (event, currentIndex) {
                //NOTE: Submit the form, if all validation passed.
                console.log("Form can be submitted using submit method. E.g. $('#basic-form').submit()");

                $("#basic-form").submit();

            }
        });
        return $form_container;
    },
        //creates form with validation
        FormWizard.prototype.createValidatorForm = function ($form_container) {
            $form_container.validate({
                errorPlacement: function errorPlacement(error, element) {
                    element.after(error);
                }
            });
            $form_container.children("div").steps({
                headerTag: "h3",
                bodyTag: "section",
                transitionEffect: "slideLeft",
                onStepChanging: function (event, currentIndex, newIndex) {
                    $form_container.validate().settings.ignore = ":disabled,:hidden";
                    return $form_container.valid();
                },
                onFinishing: function (event, currentIndex) {
                    $form_container.validate().settings.ignore = ":disabled";
                    return $form_container.valid();
                },
                onFinished: function (event, currentIndex) {

                    var form = $('#wizard-validation-form')[0];
                    var formData = new FormData(form);
                    for (var pair of formData.entries()) {
                        console.log(pair[0] + ', ' + pair[1]);
                    }

                    $.ajax({
                        url: window.location.protocol + '//' + window.location.hostname + (location.port ? ':' + location.port : '') + '/api/v1/register/client',
                        type: 'POST',
                        crossDomain: true,
                        data: formData,
                        dataType: 'json',
                        enctype: 'multipart/form-data',
                        async: true,
                        cache: false,
                        contentType: false,
                        processData: false,
                        beforeSend: function () {
                            // Show image container
                            $("#loadMe").modal({
                                backdrop: "static", //remove ability to close modal with click
                                keyboard: false, //remove option to close with keyboard
                                show: true //Display loader!
                            });
                        },
                        complete: function (data) {
                            // Hide image container
                            $("#loadMe").modal("hide");
                        },
                        success: function (json) {
                            console.log("Raw json data", json['data']);
                            console.log("Stringified", JSON.stringify(json));
                            window.location.href = protocol + '//' + hostname + (location.port ? ':' + location.port : '') + '/dashboard';
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            var error = $.parseJSON(jqXHR.responseText);
                            //Append error to modal
                            console.log('Error occurred ::::::: ', error.message)
                        }
                    });
                }
            });

            return $form_container;
        },
        //creates form with validation
        FormWizard.prototype.createFeProgramForm = function ($form_container) {
            $form_container.validate({
                errorPlacement: function errorPlacement(error, element) {
                    element.after(error);
                }
            });
            $form_container.children("div").steps({
                headerTag: "h3",
                bodyTag: "section",
                transitionEffect: "slideLeft",
                onStepChanging: function (event, currentIndex, newIndex) {
                    $form_container.validate().settings.ignore = ":disabled,:hidden";
                    return $form_container.valid();
                },
                onFinishing: function (event, currentIndex) {
                    $form_container.validate().settings.ignore = ":disabled";
                    return $form_container.valid();
                },
                onFinished: function (event, currentIndex) {
                    var form = "create-fe-program-wizard";
                    var rows_selected = table.column(0).checkboxes.selected();
                    console.log('ROWS SELECTED', rows_selected);
                    $.each(rows_selected, function (index, rowId) {
                        $(form).append(
                            $('<input>')
                                .attr('type', 'hidden')
                                .attr('name', 'id[]')
                                .val(rowId)
                        );
                    });

                    var customerArray;
                    // If some rows are selected
                    if (rows_selected.length) {
                        console.log('ROWS SELECTED');
                        var customerIdsArr = [];
                        customerIdsArr = rows_selected.join(",");
                        customerArray = customerIdsArr.split(",");
                    } else {
                        console.log('ROWS NOT SELECTED');
                        customerArray = [];
                    }

                    var dateSelected = $('#datepicker').val();
                    console.log('Date selected ::::::::::: ', timeSelected);
                    var timeSelected = $('#timepicker').val();
                    console.log('Time selected ::::::::::: ', timeSelected);
                    var dateTime = dateSelected + ' ' + timeSelected;
                    console.log('DateTime ::::::::::: ', dateTime);

                    var addProgramInfo = {
                        "name": $('#programName').val(),
                        "mode": $('#mode').val(),
                        "start_date": dateTime,
                        "modules": $('select#module_select').val(),
                        "customers": customerArray
                    };
                    var addProgramInfoJson = JSON.stringify(addProgramInfo);
                    console.log("Final json ::::::::::: " + addProgramInfoJson);

                    $.ajax({
                        url: window.location.protocol + '//' + window.location.hostname + (location.port ? ':' + location.port : '') + '/api/v1/fe/program/add',
                        type: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        crossDomain: true,
                        data: addProgramInfoJson,
                        dataType: 'json',
                        beforeSend: function () {
                            // Show image container
                            $("#loadMe").modal({
                                backdrop: "static", //remove ability to close modal with click
                                keyboard: false, //remove option to close with keyboard
                                show: true //Display loader!
                            });
                        },
                        complete: function (data) {
                            // Hide image container
                            $("#loadMe").modal("hide");
                        },
                        success: function (json) {
                            console.log(json);
                            $("form#create-fe-program-wizard")[0].reset();
                            swal({
                                title: "Success!",
                                text: json.message,
                                icon: "success",
                            });
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            var error = $.parseJSON(jqXHR.responseText);
                            swal({
                                title: "Failed!",
                                text: error.message,
                                icon: "error",
                            });
                        }
                    });
                }
            });

            return $form_container;
        },
        //creates vertical form
        FormWizard.prototype.createVertical = function ($form_container) {
            $form_container.steps({
                headerTag: "h3",
                bodyTag: "section",
                transitionEffect: "fade",
                stepsOrientation: "vertical"
            });
            return $form_container;
        },
        FormWizard.prototype.init = function () {
            //initialzing various forms

            //basic form
            this.createBasic($("#basic-form"));

            //form with validation
            this.createValidatorForm($("#wizard-validation-form"));

            //Add Fe program form
            this.createFeProgramForm($("#create-fe-program-wizard"));

            //vertical form
            this.createVertical($("#wizard-vertical"));
        },
        //init
        $.FormWizard = new FormWizard, $.FormWizard.Constructor = FormWizard
}(window.jQuery),

//initializing 
    function ($) {
        "use strict";
        $.FormWizard.init()
    }(window.jQuery);