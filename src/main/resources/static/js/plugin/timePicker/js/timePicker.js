var keycodes = {
    LEFT_ARROW: 37,
    UP_ARROW: 38,
    RIGHT_ARROW: 39,
    DOWN_ARROW: 40,
    A: 65,
    Z: 90
};

TimePicker = function (element, options) {
    options = options || {};

    this.el = RL.$(element);

    this.options = {
        stepHours: 1,
        stepMinutes: 1,
        stepSeconds: 10,

        showSeconds: false,

        hideInput: true,

        imperial: false,
        am: 'AM',
        pm: 'PM'
    };

    this.options = RL.mergeObjects(this.options, options);

    this.setValue(this.el.getAttribute('value'));
    this.buildHTML();

    this.refreshInputs();
    this.bindEvents();
};

TimePicker.prototype.buildHTML = function () {
    this.pickerDiv = document.createElement('div');
    this.pickerDiv.className += ' ui timePicker';

    this.hoursInput = document.createElement('input');
    this.hoursInput.setAttribute("style", "width:30%;border:#fff;text-align:center");
    this.hoursInput.value = this.value.hours;
    this.hoursInput.setAttribute('maxlength', 2);

    this.minutesInput = document.createElement('input');
    this.minutesInput.setAttribute("style", "width:30%;border:#fff;text-align:center");
    this.minutesInput.value = this.value.minutes;
    this.minutesInput.setAttribute('maxlength', 2);

    if (this.options.showSeconds) {
        this.secondsInput = document.createElement('input');
        this.secondsInput.setAttribute("style", "width:30%;border:#fff;text-align:center");
        this.secondsInput.value = this.value.seconds;
        this.secondsInput.setAttribute('maxlength', 2);
    }

    if (this.options.imperial) {
        this.imperialInput = document.createElement('input');
        this.imperialInput.className += ' imperial';
        this.imperialInput.value = this.getImperial();
        this.imperialInput.setAttribute('maxlength', 2);
    }


    var colonDiv1 = document.createElement('span');
    var colonDiv2 = document.createElement('span');
    var colonDiv3 = document.createElement('span');

    colonDiv1.setAttribute("style", "line-height:32px;text-align:center");
    colonDiv2.setAttribute("style", "line-height:32px;text-align:center");
    colonDiv3.setAttribute("style", "line-height:32px;text-align:center");

    colonDiv1.innerHTML = ':';
    colonDiv2.innerHTML = ':';
    colonDiv3.innerHTML = ':';

    this.pickerDiv.appendChild(this.hoursInput);
    this.pickerDiv.appendChild(colonDiv1);
    this.pickerDiv.appendChild(this.minutesInput);

    if (this.options.showSeconds) {
        this.pickerDiv.appendChild(colonDiv2);
        this.pickerDiv.appendChild(this.secondsInput);
    }
    if (this.options.imperial) {
        this.pickerDiv.appendChild(colonDiv3);
        this.pickerDiv.appendChild(this.imperialInput);
    }

    RL.after(this.el, this.pickerDiv);

    if (this.options.hideInput) {
        this.el.style.display = 'none';
    }

};

TimePicker.prototype.toString = function () {
    var string = this.hoursInput.value + ':' + this.minutesInput.value;
    if (this.options.showSeconds) {
        string += ':' + this.secondsInput.value;
    }
    if (this.options.imperial) {
        string += ' ' + this.imperialInput.value;
    }
    return string;
};


TimePicker.prototype.setValue = function (timeString) {
    var values = timeString.split(':');
    this.value = {
        hours: parseInt(values[0], 10),
        minutes: parseInt(values[1], 10),
        seconds: parseInt(values[2], 10)
    };
};

TimePicker.prototype.refreshInputs = function () {
    if (this.value.seconds >= 60) {
        this.value.minutes++;
        this.value.seconds = this.value.seconds % 60;
    }
    else if (this.value.seconds < 0) {
        this.value.minutes--;
        this.value.seconds = 60 + this.value.seconds;
    }

    if (this.value.minutes >= 60) {
        this.value.hours++;
        this.value.minutes = this.value.minutes % 60;
    }
    else if (this.value.minutes < 0) {
        this.value.hours--;
        this.value.minutes = 60 + this.value.minutes;
    }

    if (this.value.hours % 24 < 0) {
        this.value.hours = 23;
    }
    else if (this.value.hours >= 24) {
        this.value.hours = 0;
    }

    var hours = this.value.hours < 10 ? '0' + this.value.hours : this.value.hours,
        minutes = this.value.minutes < 10 ? '0' + this.value.minutes : this.value.minutes,
        seconds = this.value.seconds < 10 ? '0' + this.value.seconds : this.value.seconds;

    if (this.options.imperial) {
        this.hoursInput.value = this.getImperialHours();
        this.imperialInput.value = this.getImperial();
    }
    else {
        this.hoursInput.value = hours;
    }

    this.minutesInput.value = minutes;

    if (this.options.showSeconds) {
        this.secondsInput.value = seconds;
    }

    this.el.value = this.toString();
};

TimePicker.prototype.getImperial = function () {
    if (this.value.hours > 12) {
        return this.options.pm;
    }
    return this.options.am;
};

TimePicker.prototype.getImperialHours = function () {
    var hours;

    if (this.value.hours > 12) {
        hours = this.value.hours % 12;
    }
    else {
        hours = this.value.hours;
    }
    return hours < 10 ? '0' + hours : hours;
};

TimePicker.prototype.bindEvents = function () {
    var $this = this;
    this.hoursInput.onkeydown = function (e) {
        $this.hoursKeyDown(e);
    };
    this.minutesInput.onkeydown = function (e) {
        $this.minutesKeyDown(e);
    };

    this.hoursInput.onblur = function (e) {
        $this.onBlur(e);
    };
    this.minutesInput.onblur = function (e) {
        $this.onBlur(e);
    };

    if (this.options.showSeconds) {
        this.secondsInput.onkeydown = function (e) {
            $this.secondsKeyDown(e);
        };
        this.secondsInput.onblur = function (e) {
            $this.onBlur(e);
        };
    }
    if (this.options.imperial) {
        this.imperialInput.onkeydown = function (e) {
            $this.imperialKeyDown(e);
        };
        this.imperialInput.onblur = function (e) {
            $this.onBlur(e);
        };
    }
};


TimePicker.prototype.hoursKeyDown = function (e) {
    if (this.disableLetterInput(e)) {
        return;
    }
    switch (e.keyCode) {
        case keycodes.UP_ARROW :
            RL.preventDefault(e);
            this.value.hours = (this.value.hours + this.options.stepHours);
            this.refreshInputs();
            break;
        case keycodes.DOWN_ARROW :
            RL.preventDefault(e);
            this.value.hours = (this.value.hours - this.options.stepHours);
            this.refreshInputs();
            break;
        case keycodes.RIGHT_ARROW :
            RL.preventDefault(e);
            this.minutesInput.focus();
            break;
    }

};
TimePicker.prototype.minutesKeyDown = function (e) {
    if (this.disableLetterInput(e)) {
        return;
    }
    switch (e.keyCode) {
        case keycodes.UP_ARROW :
            RL.preventDefault(e);
            this.value.minutes = (this.value.minutes + this.options.stepMinutes);
            this.refreshInputs();
            break;
        case keycodes.DOWN_ARROW :
            RL.preventDefault(e);
            this.value.minutes = (this.value.minutes - this.options.stepMinutes);
            this.refreshInputs();
            break;
        case keycodes.LEFT_ARROW :
            RL.preventDefault(e);
            this.hoursInput.focus();
            break;
        case keycodes.RIGHT_ARROW :
            RL.preventDefault(e);
            if (this.options.showSeconds) {
                this.secondsInput.focus();
            }
            else if (this.options.imperial) {
                this.imperialInput.focus();
            }
            break;
    }
};

TimePicker.prototype.secondsKeyDown = function (e) {
    if (this.disableLetterInput(e)) {
        return;
    }
    switch (e.keyCode) {
        case keycodes.UP_ARROW :
            RL.preventDefault(e);
            this.value.seconds = (this.value.seconds + this.options.stepSeconds);
            this.refreshInputs();
            break;
        case keycodes.DOWN_ARROW :
            RL.preventDefault(e);
            this.value.seconds = (this.value.seconds - this.options.stepSeconds);
            this.refreshInputs();
            break;
        case keycodes.LEFT_ARROW :
            RL.preventDefault(e);
            this.minutesInput.focus();
            break;
        case keycodes.RIGHT_ARROW :
            if (this.options.imperial) {
                RL.preventDefault(e);
                this.imperialInput.focus();
            }
            break;
    }
};

TimePicker.prototype.imperialKeyDown = function (e) {
    //this.disableLetterInput(e);
    switch (e.keyCode) {
        case keycodes.UP_ARROW :
        case keycodes.DOWN_ARROW :
            RL.preventDefault(e);
            this.value.hours = (this.value.hours + 12) % 24;
            this.refreshInputs();
            break;
        case keycodes.LEFT_ARROW :
            RL.preventDefault(e);
            if (this.options.showSeconds) {
                this.secondsInput.focus();
            }
            else {
                this.minutesInput.focus();
            }
            break;
    }
};

TimePicker.prototype.disableLetterInput = function (e) {
    if (!e.ctrlKey && !e.metaKey) {
        if (e.keyCode >= keycodes.A && e.keyCode <= keycodes.Z) {
            RL.preventDefault(e);
            return true;
        }
    }
    return false;
};

TimePicker.prototype.onBlur = function (e) {
    var hours = parseInt(this.hoursInput.value, 10),
        minutes = parseInt(this.minutesInput.value, 10);

    if (this.options.showSeconds) {
        var seconds = parseInt(this.secondsInput.value, 10);
        if (!isNaN(seconds)) {
            this.value.seconds = seconds;
        }
    }

    if (!isNaN(hours)) {
        this.value.hours = hours;
        if (this.options.imperial && this.imperialInput.value == this.options.pm) {
            this.value.hours += 12;
        }
    }
    if (!isNaN(minutes)) {
        this.value.minutes = minutes;
    }
    this.refreshInputs();
};