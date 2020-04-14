$(function () {
    'use strict';

    var previewNode = document.querySelector("#template");
    previewNode.id = "";
    var previewTemplate = previewNode.parentNode.innerHTML;
    previewNode.parentNode.removeChild(previewNode);

    // Now that the DOM is fully loaded, create the dropzone, and setup the
    // event listeners
    var myDropzone = new Dropzone("#drop_zone", {
        url: protocol + '//' + hostname + (location.port ? ':' + location.port : '') + '/api/v1/statement/upload',
        acceptedFiles: "application/pdf",
        thumbnailWidth: 80,
        thumbnailHeight: 80,
        parallelUploads: 20,
        previewTemplate: previewTemplate,
        autoQueue: false, // Make sure the files aren't queued until manually added
        previewsContainer: "#previews", // Define the container to display the previews
        clickable: ".fileinput-button" // Define the element that should be used as click trigger to select files.
    });

    myDropzone.on("addedfile", function (file) {
        // Hookup the start button
        file.previewElement.querySelector(".start").onclick = function () {
            myDropzone.enqueueFile(file);
        };
    });

    // Update the total progress bar
    myDropzone.on("totaluploadprogress", function (progress) {
        document.querySelector("#total-progress .dropzone-progress-bar").style.width = progress + "%";
    });

    myDropzone.on("sending", function (file) {
        // Show the total progress bar when upload starts
        document.querySelector("#total-progress").style.opacity = "1";
        // And disable the start button
        file.previewElement.querySelector(".start").setAttribute("disabled", "disabled");
    });

    // Hide the total progress bar when nothing's uploading anymore
    myDropzone.on("queuecomplete", function (progress) {
        document.querySelector("#total-progress").style.opacity = "0";
    });

    // Setup the buttons for all transfers
    // The "add files" button doesn't need to be setup because the config
    // `clickable` has already been specified.
    document.querySelector("#actions .start").onclick = function () {
        myDropzone.enqueueFiles(myDropzone.getFilesWithStatus(Dropzone.ADDED));
    };
    document.querySelector("#actions .cancel").onclick = function () {
        myDropzone.removeAllFiles(true);
    };

    // Now upload files
    myDropzone.uploadFiles = function (files) {
        var self = this;

        for (var i = 0; i < files.length; i++) {
            var file = files[i];

            var formData = new FormData();
            formData.append('file', file);

            //Make Ajax call to upload statement
            $.ajax({
                url: protocol + '//' + hostname + (location.port ? ':' + location.port : '') + '/api/v1/statement/upload',
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
                    file.upload = {
                        progress: 10,
                        total: file.size,
                        bytesSent: file.size
                    };
                    self.emit('uploadprogress', file, file.upload.progress, file.upload.bytesSent);
                },
                complete: function (data) {
                },
                success: function (json) {
                    console.log("Success response ::::::: ", json);

                    file.upload = {
                        progress: 50,
                        total: file.size,
                        bytesSent: file.size
                    };
                    self.emit('uploadprogress', file, file.upload.progress, file.upload.bytesSent);

                    var statementId = null;
                    $.each(json, function (key, value) {
                        if (key == 'data') {
                            var dataObject = value;
                            statementId = dataObject.id;
                            console.log('Statement id: ', statementId);
                        }
                    });

                    if (statementId != null) {
                        //Poll for callback received
                        poll_statement_progress(file, statementId);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    var error = $.parseJSON(jqXHR.responseText);
                    console.log("Error response ::::::: ", error);

                    self.emit('uploadprogress', file, 100, file.upload.bytesSent);
                    self.emit('error', file, error.message);

                    if (error.status == 409) {
                        self.emit("success", file, 'success', null);
                        self.emit("complete", file);
                        self.processQueue();
                    }
                }
            });
        }

        function poll_statement_progress(file, id) {
            console.log('Start long polling for statement id : ', id);
            setTimeout(function(file, id) {
                return function() {
                    $.ajax({
                        url: protocol + '//' + hostname + (location.port ? ':' + location.port : '') + '/api/v1/statement/upload/status',
                        type: 'GET',
                        data: {
                            statementId: id
                        },
                        dataType: "json",
                        success: function (json) {
                            console.log(json);
                            var statementId = null;
                            var status = null;
                            $.each(json, function (key, value) {
                                if (key == 'data') {
                                    var dataObject = value;
                                    statementId = dataObject.id;
                                    status = dataObject.status;
                                }
                            });
                            if (statementId != null && status != null) {
                                if (status == 1 || status == 2) {
                                    poll_statement_progress(file, statementId);
                                } else if (status == 3) {
                                    file.upload = {
                                        progress: 100,
                                        total: file.size,
                                        bytesSent: file.size
                                    };
                                    self.emit('uploadprogress', file, file.upload.progress, file.upload.bytesSent);
                                    if (file.upload.progress == 100) {
                                        file.status = Dropzone.SUCCESS;
                                        self.emit("success", file, 'success', null);
                                        self.emit("complete", file);
                                        self.processQueue();
                                    }
                                } else {
                                    self.emit('uploadprogress', file, 100, file.upload.bytesSent);
                                    self.emit('error', file, "A technical error occurred while processing document, please contact support");
                                }
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            var error = $.parseJSON(jqXHR.responseText);
                            console.log("Error response ::::::: ", error);

                            self.emit('uploadprogress', file, 100, file.upload.bytesSent);
                            self.emit('error', file, error.message);
                            self.processQueue();
                        }
                    });
                };
            }(file, id), 1000);
        }
    }

});


