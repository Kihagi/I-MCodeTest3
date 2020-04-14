/*
 * jQuery File Upload
 * https://github.com/blueimp/jQuery-File-Upload
 *
 */

/* global $ */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#statement-upload').fileupload({
      // Uncomment the following to send cross-domain cookies:
      xhrFields: {withCredentials: true},
      acceptFileTypes: /([./])(pdf)$/i,
      disableImagePreview: true,
      previewThumbnail: false,
      url: '/api/v1/statement/upload'
    });

    // Load existing files:
    $('#statement-upload').addClass('fileupload-processing');
    $.ajax({
        // Uncomment the following to send cross-domain cookies:
        xhrFields: {withCredentials: true},
        url: $('#statement-upload').fileupload('option', 'url'),
        dataType: 'json',
        context: $('#statement-upload')[0]
    }).always(function () {
        $(this).removeClass('fileupload-processing');
    }).done(function (result) {
        $(this).fileupload('option', 'done')
        // eslint-disable-next-line new-cap
            .call(this, $.Event('done'), {result: result});
    });
});
