document.write('<style>div.accordion_content_1{height:0px;overflow:hidden;}</style>');
document.write('<style>div.accordion_content_1_open{height:auto;overflow:hidden;}</style>');
document.write('<style>div.accordion_content_2{height:0px;overflow:hidden;}</style>');
document.write('<style>div.accordion_content_2_open{height:auto;overflow:hidden;}</style>');
document.write('<style>div.accordion_content_3{height:0px;overflow:hidden;}</style>');
document.write('<style>div.accordion_content_3_open{height:auto;overflow:hidden;}</style>');


window.addEvent('domready', function () {
    if ($('NavLeft').getElement('a.on')) {
        $('NavLeft').getElement('a.on').getParents('li').each(function (el) {
            if (el.getElement('div[class^=accordion_toggler_]') != null) {
                tab_level_index = el.className.split('_');
                level_index = tab_level_index[3].trim();
                el.getElement('div.accordion_toggler_' + level_index).addClass('open');
                el.getElement('div.accordion_toggler_' + level_index).addClass('open_on_init');
                el.getElement('div.accordion_toggler_' + level_index).getNext().removeClass('accordion_content_' + level_index);
                el.getElement('div.accordion_toggler_' + level_index).getNext().addClass('accordion_content_' + level_index + '_open');
            }
        })
    }
    var parrent_height_value;
    var tweenerFunction = function (lekel, tween_type) {
        if (tween_type == 'opening') {
            current_height_value = lekel.getStyle('height');
            lekel.setStyle('height', 'auto');
            lekel_real_height = lekel.getSize().y;
            lekel.setStyle('height', '0');
            lekel.tween('height', lekel_real_height);
            parrent_height_value=parrent_height_value+lekel_real_height;
        }
        else {
            lekel_real_height = lekel.getSize().y;
            lekel.tween('height', '0');
            parrent_height_value=parrent_height_value-lekel_real_height;
        }
    }
    $$('#NavLeft ul li.li_accordion_toggler_1').each(function (el) {
        $(el).getElements('div[class^=accordion_toggler_]').addEvent('click', function (toggler) {
            tab_level_index = this.className.split('_');
            tab_level_index_no_open_info = tab_level_index[2].split(' open');
            level_index = tab_level_index_no_open_info[0].trim();
            if (this.hasClass('open_on_init') == false) {
                parentElement = this.getNext().parentElement.parentElement.parentElement;
                parrent_height_value=parentElement.getSize().y;
                if (this.getNext().className == 'accordion_content_' + level_index + '_open') {
                    tweenerFunction(this.getNext(), 'closing');
                    this.getNext().removeClass('accordion_content_' + level_index + '_open');
                    this.getNext().addClass('accordion_content_' + level_index);
                    this.removeClass('open');
                }
                else {
                    $$('div.accordion_toggler_' + level_index).each(function (el_toggler) {
                        if (el_toggler.hasClass('open') == true && el_toggler.hasClass('open_on_init') == false) {
                            tweenerFunction(el_toggler.getNext(), 'closing');
                            el_toggler.getNext().removeClass('accordion_content_' + level_index + '_open');
                            el_toggler.getNext().addClass('accordion_content_' + level_index);
                            el_toggler.removeClass('open');
                        }
                    })
                    parrent_height_value=parrent_height_value + this.getNext().getSize().y;
                    tweenerFunction(this.getNext(), 'opening');
                    this.getNext().removeClass('accordion_content_' + level_index);
                    this.getNext().addClass('accordion_content_' + level_index + '_open');
                    this.addClass('open');                    
                }
                if(level_index==2){
                    parentElement.tween('height', parrent_height_value);

                }
            }
        });
    });
})