let lastImageIndex = 0;  
window.onload = function () {
  'use strict';

  var Viewer = window.Viewer;
  var console = window.console || { log: function () {} };
  var pictures = document.querySelector('.docs-pictures');
  var toggles = document.querySelector('.docs-toggles');
  var buttons = document.querySelector('.docs-buttons');
  var options = {
    // inline: true,
    url: 'data-original',
    ready: function (e) {
      console.log(e.type);
    },
    show: function (e) {
      console.log(e.type);
    },
    shown: function (e) {
      console.log(e.type);
    },
    hide: function (e) {
      console.log(e, e.type);
      $.cookie('full-view', 'false');
    },
    hidden: function (e) {
      console.log(e, e.type);
      $.cookie('full-view', 'false');
    },
    view: function (e) {
      console.log(e, e.type);
      $.cookie('full-view', 'true');
    },
    viewed: function (e) {
      $.cookie('full-view', 'true');
      console.log(e.detail.index, e.type, e);
      $.cookie('view-detail-index', e.detail.index);
    },
    move: function (e) {
      console.log(e.type);
    },
    moved: function (e) {
      console.log(e.type);
    },
    rotate: function (e) {
      console.log(e.type);
    },
    rotated: function (e) {
      console.log(e.type);
    },
    scale: function (e) {
      console.log(e.type);
    },
    scaled: function (e) {
      console.log(e.type);
    },
    zoom: function (e) {
      console.log(e.type);
    },
    zoomed: function (e) {
      console.log(e.type);
    },
    play: function (e) {
      console.log(e.type);
    },
    stop: function (e) {
      console.log(e.type);
    }
  };
	console.log('toggles=', toggles, $(toggles), );
	$(toggles).find('input').each(function(index, ele) {
		console.log(index, ele);
		var name = ele.getAttribute('name');
		if ( name !== 'inline' ) {
			options[name] = ele.checked;
		}
	});
  var viewer = new Viewer(pictures, options);

  function toggleButtons(mode) {
    var targets;
    var target;
    var length;
    var i;

    if (/modal|inline|none/.test(mode)) {
      targets = buttons.querySelectorAll('button[data-enable]');

      for (i = 0, length = targets.length; i < length; i++) {
        target = targets[i];
        target.disabled = true;

        if (String(target.getAttribute('data-enable')).indexOf(mode) > -1) {
          target.disabled = false;
        }
      }
    }
  }
  setTimeout(function() {
	if ( $.cookie('full-view') == 'true' ) {
		$('button[data-method=view]').trigger('click');
		document.focus();
	}
  }, 300);

  function addEventListener(element, type, handler) {
    if (element.addEventListener) {
      element.addEventListener(type, handler, false);
    } else if (element.attachEvent) {
      element.attachEvent('on' + type, handler);
    }
  }

  toggleButtons(options.inline ? 'inline' : 'modal');

  toggles.onchange = function (event) {
    var e = event || window.event;
    var input = e.target || e.srcElement;
    var name;

    if (viewer) {
      name = input.getAttribute('name');
      options[name] = name === 'inline' ? JSON.parse(input.getAttribute('data-value')) : input.checked;
      viewer.destroy();
      viewer = new Viewer(pictures, options);
      toggleButtons(options.inline ? 'inline' : 'modal');
    }
  };

  buttons.onclick = function (event) {
	console.log('buttons.onclick check.')
    var e = event || window.event;
    var button = e.target || e.srcElement;
    var method = button.getAttribute('data-method');
    var target = button.getAttribute('data-target');
    var args = JSON.parse(button.getAttribute('data-arguments')) || [];

    if (viewer && method) {
      if (target) {
        viewer[method](document.querySelector(target).value);
      } else {
        viewer[method](args[0], args[1]);
      }

      switch (method) {
        case 'scaleX':
        case 'scaleY':
          args[0] = -args[0];
          break;

        case 'destroy':
          viewer = null;
          toggleButtons('none');
          break;
      }
    }
  };

  // $('[data-toggle="tooltip"]').tooltip();

  document.addEventListener('keyup', (event) => {
	  const keyName = event.key;
	  console.log('keyName = '+keyName);
	  console.log('event.ctrlKey = '+event.ctrlKey);
	  console.log('event.altKey  = '+event.altKey);
	  console.log('lastImageIndex = ' + lastImageIndex);
	  console.log('parseInt($.cookie(view-detail-index)) = ' + parseInt($.cookie('view-detail-index')));
	
	  // As the user releases the Ctrl key, the key is no longer active,
	  // so event.ctrlKey is false.
	  
	  if ( event.ctrlKey ) {
		  if (keyName === 'ArrowRight') {
		    $('.buttonNextPage').get(0).click();
		  } else if (keyName === 'ArrowLeft') {
			$('.buttonPrevPage').get(0).click();
		  }
	  }
	  if ( lastImageIndex > -1 ) {
		  var currentImageIndex = 0;
		  try{ currentImageIndex = parseInt($.cookie('view-detail-index'));
		  }catch(e){}
		  
		  if (keyName === 'ArrowRight') {
			if ( currentImageIndex === lastImageIndex ) {
			    $('.buttonNextPage').get(0).click();
			}
		  } else if (keyName === 'ArrowLeft') {
			if ( currentImageIndex === lastImageIndex ) {
			    $('.buttonPrevPage').get(0).click();
			}
		  }
		  lastImageIndex = currentImageIndex
	  }
	  if (keyName === 'End') {
		$('#viewIndex').val($('.docs-pictures img').length-1);
		$('button[data-method=view]').trigger('click');
	  } else if (keyName === 'Home') {
		$('#viewIndex').val(0);
		$('button[data-method=view]').trigger('click');
	  } else {
	  }
	}, false);

};
