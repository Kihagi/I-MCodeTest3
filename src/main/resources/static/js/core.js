/* -----------------------------------------------------------------------------
core js scripts

File:           JS Core
Version:        1.0
Author:         Mathenge
-------------------------------------------------------------------------------- */

/*
Fetch characters for listing
 */
$(window).load(function () {
    if (document.location.pathname === "/") {
        load_character_listing();
    }
});

function load_character_listing() {
    console.log('Fetch characters to list');

    $('#character_list').DataTable({
        "processing": true,
        "serverSide": true,
        "pageLength": 10,
        "ajax": {
            "url": "/characters/paginated",
            "data": function (data) {
                //process data before sent to server.
            }
        },
        "columns": [
            {"data": "name", "name": "Name", "title": "Name"},
            {"data": "gender", "name": "Gender", "title": "Gender"},
            {"data": "birthYear", "name": "Birth Year", "title": "Birth Year"},
            {
                "data": null,
                "bSortable": false,
                "mRender": function (data, type, full) {
                    var character_favorite = data.favorite;
                    if (character_favorite == 1) {
                        return '<i class="fa fa-heart"></i>';
                    } else {
                        return '<i class="fa fa-heart-o"></i>';
                    }
                }
            },
            {
                "data": null,
                "bSortable": false,
                "mRender": function (data, type, full) {
                    var character_url = data.url;
                    return '<a id="btn-view-character" class="btn btn-success-outline waves-effect waves-light view-character" href="javascript:void(0);" data-id="' + character_url + '" ><i class="fa fa-eye"></i>&nbsp; VIEW DETAILS</a>';
                }
            }
        ]
    });
}

/*
Character details page loaded
 */
$(window).load(function () {
    if (document.location.pathname === "/details") {
        var character_id = $('#character_id').val();
        console.log("Character id received :::::::::::::: ", character_id);
        load_character_details(character_id);
    }
});

/*
Fetch caharacter details
 */
function load_character_details(id) {

    $.ajax({
        url: protocol + '//' + hostname + (location.port ? ':' + location.port : '') + '/characters',
        type: 'GET',
        data: {
            id: id
        },
        crossDomain: true,
        dataType: 'json',
        complete: function (data) {
            // Hide image container
            $(".loader-wrapper").find(".loader").remove();
        },
        success: function (json) {
            console.log("Stringified", JSON.stringify(json));
            $.each(json, function (key, value) {
                if (key == 'data') {
                    var dataObject = value;
                    console.log(dataObject);

                    //Update favorite button
                    if (dataObject.character_info.favorite == 1) {
                        $("#character-favorite").append('<a id="btn-character-favorite" class="btn btn-danger-outline waves-effect waves-light remove-character-favorite" href="javascript:void(0);" data-url="' + dataObject.character_info.url + '" ><i class="fa fa-heart-o"></i>&nbsp; REMOVE FROM FAVORITES</a>');
                    } else {
                        $("#character-favorite").append('<a id="btn-character-favorite" class="btn btn-success-outline waves-effect waves-light add-character-favorite" href="javascript:void(0);" data-url="' + dataObject.character_info.url + '" ><i class="fa fa-heart-o"></i>&nbsp; ADD TO FAVORITES</a>');
                    }

                    /*
                    Map character Info view data
                     */
                    $('#profile-card').show();
                    $("#character-profile-view").append("<h6 class='m-t-20 m-b-10 font-14 target'>Name: &nbsp;" + dataObject.character_info.name + "</h6>");
                    $("#character-profile-view").append("<h6 class='m-t-20 m-b-10 font-14 target'>Height: &nbsp;" + dataObject.character_info.height + "</h6>");
                    $("#character-profile-view").append("<h6 class='m-t-20 m-b-10 font-14 target'>Mass: &nbsp;" + dataObject.character_info.mass + "</h6>");
                    $("#character-profile-view").append("<h6 class='m-t-20 m-b-10 font-14 target'>Hair Color: &nbsp;" + dataObject.character_info.hair_color + "</h6>");
                    $("#character-profile-view").append("<h6 class='m-t-20 m-b-10 font-14 target'>Skin Color: &nbsp;" + dataObject.character_info.skin_color + "</h6>");
                    $("#character-profile-view").append("<h6 class='m-t-20 m-b-10 font-14 target'>Eye Color: &nbsp;" + dataObject.character_info.eye_color + "</h6>");
                    $("#character-profile-view").append("<h6 class='m-t-20 m-b-10 font-14 target'>Brth Year: &nbsp;" + dataObject.character_info.birth_year + "</h6>");

                    /*
                    Map character films
                     */
                    $('#films-card').show();
                    var filmArray = dataObject.film_list;
                    if ($.isArray(filmArray) && filmArray.length > 0) {
                        //Initialize table
                        film_table = $('#character-films-table').DataTable({
                            processing: true, //Feature control the processing indicator.
                            data: filmArray,
                            serverSide: false,
                            ordering: true,
                            searching: false,
                            paging: false,
                            info: false,
                            aaSorting: [],
                            //order: [],
                            dom: 'Bfrtip',
                            lengthMenu: [
                                [10, 25, 50, -1],
                                ['10 rows', '25 rows', '50 rows', 'Show all']
                            ],
                            buttons: [
                                'excel', 'pdf', 'colvis'
                            ],
                            columns: [
                                {data: "title"},
                                {data: "episode_id"},
                                {data: "director"},
                                {data: "producer"},
                                {data: "release_date"}
                            ],
                            columnDefs: [
                                {
                                    "targets": 0
                                }
                            ],
                            select: {
                                'style': 'multi'
                            },
                            order: [[1, 'asc']]
                        });
                        film_table.buttons().container()
                            .appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');
                    }

                    /*
                    Map character species
                     */
                    $('#species-card').show();
                    var speciesArray = dataObject.species_list;
                    if ($.isArray(speciesArray) && speciesArray.length > 0) {
                        //Initialize table
                        species_table = $('#character-species-table').DataTable({
                            processing: true, //Feature control the processing indicator.
                            data: speciesArray,
                            serverSide: false,
                            ordering: false,
                            searching: false,
                            paging: false,
                            info: false,
                            aaSorting: [],
                            //order: [],
                            dom: 'Bfrtip',
                            lengthMenu: [
                                [10, 25, 50, -1],
                                ['10 rows', '25 rows', '50 rows', 'Show all']
                            ],
                            buttons: [
                                'excel', 'pdf', 'colvis'
                            ],
                            columns: [
                                {data: "name"},
                                {data: "classification"},
                                {data: "designation"},
                                {data: "average_height"},
                                {data: "average_lifespan"}
                            ],
                            columnDefs: [
                                {
                                    "targets": 0
                                }
                            ],
                            select: {
                                'style': 'multi'
                            },
                            order: [[1, 'asc']]
                        });
                        species_table.buttons().container()
                            .appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');
                    }

                    /*
                    Map character vehicles
                     */
                    $('#vehicles-card').show();
                    var vehiclesArray = dataObject.vehicle_list;
                    if ($.isArray(vehiclesArray) && vehiclesArray.length > 0) {
                        //Initialize table
                        vehicles_table = $('#character-vehicles-table').DataTable({
                            processing: true, //Feature control the processing indicator.
                            data: vehiclesArray,
                            serverSide: false,
                            ordering: false,
                            searching: false,
                            paging: false,
                            info: false,
                            aaSorting: [],
                            //order: [],
                            dom: 'Bfrtip',
                            lengthMenu: [
                                [10, 25, 50, -1],
                                ['10 rows', '25 rows', '50 rows', 'Show all']
                            ],
                            buttons: [
                                'excel', 'pdf', 'colvis'
                            ],
                            columns: [
                                {data: "name"},
                                {data: "model"},
                                {data: "manufacturer"},
                                {data: "cost_in_credits"},
                                {data: "length"},
                                {data: "max_atmosphering_speed"},
                                {data: "crew"}
                            ],
                            columnDefs: [
                                {
                                    "targets": 0
                                }
                            ],
                            select: {
                                'style': 'multi'
                            },
                            order: [[1, 'asc']]
                        });
                        vehicles_table.buttons().container()
                            .appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');
                    }

                    /*
                    Map character starships
                     */
                    $('#starships-card').show();
                    var startshipsArray = dataObject.starship_list;
                    if ($.isArray(startshipsArray) && startshipsArray.length > 0) {
                        //Initialize table
                        starships_table = $('#character-startships-table').DataTable({
                            processing: true, //Feature control the processing indicator.
                            data: startshipsArray,
                            serverSide: false,
                            ordering: false,
                            searching: false,
                            paging: false,
                            info: false,
                            aaSorting: [],
                            //order: [],
                            dom: 'Bfrtip',
                            lengthMenu: [
                                [10, 25, 50, -1],
                                ['10 rows', '25 rows', '50 rows', 'Show all']
                            ],
                            buttons: [
                                'excel', 'pdf', 'colvis'
                            ],
                            columns: [
                                {data: "name"},
                                {data: "model"},
                                {data: "manufacturer"},
                                {data: "cost_in_credits"},
                                {data: "length"},
                                {data: "max_atmosphering_speed"},
                                {data: "crew"}
                            ],
                            columnDefs: [
                                {
                                    "targets": 0
                                }
                            ],
                            select: {
                                'style': 'multi'
                            },
                            order: [[1, 'asc']]
                        });
                        starships_table.buttons().container()
                            .appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');
                    }
                }
            });

        },
        error: function (jqXHR, textStatus, errorThrown) {
            var error = $.parseJSON(jqXHR.responseText);
            console.log("Error response ::::::: ", error);
            swal({
                title: "Failed!",
                text: error.message,
                icon: "error",
            });
        }
    });
}

/*
Add or remove favorite
 */
function update_favorite(button, url, action) {

    // $submit_btn = $(button);
    // $loadingText = '<i class="fa fa-circle-o-notch fa-spin"></i> Please wait...';
    // if ($submit_btn.html() !== $loadingText) {
    //     $submit_btn.html($loadingText);
    // }

    var updateFavoriteInfo = {
        "character_url": url,
        "action": action
    };

    var updateFavoriteInfoJson = JSON.stringify(updateFavoriteInfo);

    // Get the form instance
    $.ajax({
        url: protocol + '//' + hostname + (location.port ? ':' + location.port : '') + '/characters/favorite',
        type: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        crossDomain: true,
        data: updateFavoriteInfoJson,
        dataType: 'json',
        success: function (json) {
            console.log(json);
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

/*
Url params
 */
var url = window.location.host;
var protocol = window.location.protocol;
var hostname = window.location.hostname;
var port = window.location.port;

var minimum = parseInt($('#minimum').val());
var lowercase = $('#lowercase').val();
var uppercase = $('#uppercase').val();
var numbers = $('#numbers').val();

$(document).ready(function () {

    $("body").on('click', '.view-character', function () {
        var character_url = $(this).data('id');
        var character_id = character_url.charAt(character_url.length - 2);
        console.log('character id is', character_id);

        window.open(
            protocol + '//' + hostname + (location.port ? ':' + location.port : '') + '/details?id=' + character_id,
            '_blank' // <- This is what makes it open in a new window.
        );
    });

    $("body").on('click', '.add-character-favorite', function () {
        var character_url = $(this).data('url');
        console.log('character url is', character_url);
        update_favorite($(this), character_url, "ADD");
    });

    $("body").on('click', '.remove-character-favorite', function () {
        var character_url = $(this).data('url');
        console.log('character url is', character_url);
        update_favorite($(this), character_url, "DELETE");
    });
});