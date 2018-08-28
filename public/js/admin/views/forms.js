$(function() {
  'use strict';

  $("#date").mask("99/99/9999");

  $('.select2').select2();

  $('textarea.summer2').summernote({
    height: 300
  });

  $.validator.setDefaults({
    submitHandler: function() {
      alert('submitted!');
    }
  });
  $(document).ready(function(){
    $("form.jvalidate").validate({
      errorElement: 'em',
      errorPlacement: function ( error, element ) {
        // Add the `help-block` class to the error element
        error.addClass( 'invalid-feedback' );
        if ( element.prop( 'type' ) === 'checkbox' ) {
          error.insertAfter( element.parent( 'label' ) );
        } else {
          error.insertAfter( element );
        }
      },
      highlight: function ( element, errorClass, validClass ) {
        $( element ).addClass( 'is-invalid' ).removeClass( 'is-valid' );
        $( element ).parents( '.form-group' ).addClass( 'has-danger' ).removeClass( 'has-success' );
      },
      unhighlight: function (element, errorClass, validClass) {
        $( element ).addClass( 'is-valid' ).removeClass( 'is-invalid' );
        $( element ).parents( '.form-group' ).addClass( 'has-success' ).removeClass( 'has-danger' );
      }
    });
  });


})
