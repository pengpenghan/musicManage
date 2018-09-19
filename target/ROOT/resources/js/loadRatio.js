$(function (ELEMENT) {

    ELEMENT.matches = ELEMENT.matches || ELEMENT.mozMatchesSelector || ELEMENT.msMatchesSelector || ELEMENT.oMatchesSelector || ELEMENT.webkitMatchesSelector;
    ELEMENT.closest = ELEMENT.closest || function closest(selector) {
        var element = this;
        while (element) {
            if (window.CP.shouldStopExecution(1)) {
                break;
            }
            if (element.matches(selector))
                break;
            element = element.parentElement;
        }
        window.CP.exitedLoop(1);
        return element;
    };
}(Element.prototype));
var i = 0;
var barPie = {
    onChange: function (e) {
        if (e.target.name != 'barPieRadioGroup')
            return;
        var scopeElm = e.target.closest('.barPie--radio');
        barPie.update(scopeElm, +e.target.value);
        if (!scopeElm.active)
            //scopeElm.querySelector('.barPie__ring').lastElementChild.addEventListener('none', barPie.clickToNull);
        scopeElm.active = 1;
    },
    update: function (scopeElm, value, speed, extraStep) {
        if (!scopeElm)
            return;
        var valueElm = scopeElm.querySelector('.barPie__value'), inital = +valueElm.innerHTML, delta = value - inital, doin;
        function step(t, elapsed) {
            t = 1 - Math.exp(-t * 7);
            var value = delta * t + inital, remainder = value % 1;
            if (t > 0.99 && (remainder > 0.9 || remainder < 0.01)) {
                value = Math.round(value);
                doin.step = function () {
                };
            } else{
                value = value.toFixed(remainder ? 1 : 0);
            }
            valueElm.innerHTML = value;
            if (extraStep)
                extraStep(t);
        }
        if (!valueElm.doin) {
            doin = new Doin(step, speed || 0.33);
            valueElm.doin = doin;
        } else
            doin = valueElm.doin;
        doin.step = step;
        doin.run();
    }
};
//document.addEventListener('change', barPie.onChnage);
var barPies = document.querySelectorAll('.barPie');
setTimeout(lazyCount, 1500);
function lazyCount() {
    var currentBarPie, toValue, itemsCount;
    function step(t) {
        var itemIdx = Math.round(itemsCount * (toValue / 100) * t);
        document.getElementById(currentBarPie.id + 'Item' + itemIdx).checked = true;
    }
    for (var i = barPies.length; i--;) {
        if (window.CP.shouldStopExecution(2)) {
            break;
        }
        currentBarPie = barPies[i];
        toValue = currentBarPie.dataset.toValue;
        if (toValue) {
            itemsCount = currentBarPie.dataset.itemsCount;
            barPie.update(currentBarPie, toValue, 1.5, step);
        }
    }
    window.CP.exitedLoop(2);
}