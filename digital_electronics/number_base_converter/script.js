let currentDecimal = null;

function toggleTheme() {
    document.body.classList.toggle('dark');
    const btn = document.getElementById('themeBtn');
    btn.textContent = document.body.classList.contains('dark') ? '☀️ Light Mode' : '🌙 Dark Mode';
}

function detectBase(s) {
    if (/^[01]+$/.test(s)) return 2;
    if (/^[0-7]+$/.test(s)) return 8;
    if (/^[0-9]+$/.test(s)) return 10;
    if (/^[0-9a-fA-F]+$/.test(s)) return 16;
    return null;
}

function convert() {
    const input = document.getElementById('numberInput').value.trim();
    const baseSel = document.getElementById('baseSelect').value;
    const results = ['binaryResult', 'decimalResult', 'hexResult', 'octalResult'];

    // Reset
    results.forEach(id => document.getElementById(id).textContent = '-');
    document.getElementById('explanationText').textContent = 'Click any result card above to see the conversion steps.';
    currentDecimal = null;

    if (!input) return;

    let base = baseSel === 'auto' ? detectBase(input) : parseInt(baseSel);
    if (!base) {
        results.forEach(id => document.getElementById(id).textContent = 'Invalid');
        return;
    }

    const decimal = parseInt(input, base);
    if (isNaN(decimal)) {
        results.forEach(id => document.getElementById(id).textContent = 'Error');
        return;
    }

    currentDecimal = decimal;

    document.getElementById('binaryResult').textContent = decimal.toString(2);
    document.getElementById('decimalResult').textContent = decimal.toString(10);
    document.getElementById('hexResult').textContent = decimal.toString(16).toUpperCase();
    document.getElementById('octalResult').textContent = decimal.toString(8);
}

function explain(type) {
    if (currentDecimal === null) {
        document.getElementById('explanationText').textContent = 'Please enter a number first!';
        return;
    }

    const d = currentDecimal;
    const steps = [];
    const names = { binary: 'Binary', decimal: 'Decimal', hex: 'Hexadecimal', octal: 'Octal' };

    if (type === 'binary') {
        steps.push(`<span class="step">Step 1: Start with decimal value: <span class="highlight">${d}</span></span>`);
        let n = d;
        let bits = [];
        while (n > 0) {
            bits.push(n % 2);
            n = Math.floor(n / 2);
        }
        if (bits.length === 0) bits.push(0);
        bits.reverse();
        steps.push(`<span class="step">Step 2: Repeatedly divide by 2 and collect remainders</span>`);
        let temp = d;
        let lines = [];
        while (temp > 0) {
            lines.push(`${temp} ÷ 2 = ${Math.floor(temp/2)} remainder ${temp % 2}`);
            temp = Math.floor(temp / 2);
        }
        lines.reverse();
        lines.forEach(l => steps.push(`<span class="step">&nbsp;&nbsp;${l}</span>`));
        steps.push(`<span class="step">Step 3: Read remainders bottom-to-top: <span class="highlight">${bits.join('')}</span></span>`);
        steps.push(`<span class="step">Step 4: Binary of ${d} = <span class="highlight">${d.toString(2)}</span></span>`);
    } else if (type === 'decimal') {
        steps.push(`<span class="step">The number is already in decimal: <span class="highlight">${d}</span></span>`);
        steps.push(`<span class="step">No conversion needed!</span>`);
    } else if (type === 'hex') {
        steps.push(`<span class="step">Step 1: Start with decimal value: <span class="highlight">${d}</span></span>`);
        let n = d;
        let hexDigits = [];
        const hexChars = '0123456789ABCDEF';
        while (n > 0) {
            hexDigits.push(hexChars[n % 16]);
            n = Math.floor(n / 16);
        }
        if (hexDigits.length === 0) hexDigits.push('0');
        hexDigits.reverse();
        steps.push(`<span class="step">Step 2: Repeatedly divide by 16 and map remainders to hex digits</span>`);
        let temp = d;
        let lines = [];
        while (temp > 0) {
            lines.push(`${temp} ÷ 16 = ${Math.floor(temp/16)} remainder ${temp % 16} → ${hexChars[temp % 16]}`);
            temp = Math.floor(temp / 16);
        }
        lines.reverse();
        lines.forEach(l => steps.push(`<span class="step">&nbsp;&nbsp;${l}</span>`));
        steps.push(`<span class="step">Step 3: Read remainders bottom-to-top: <span class="highlight">${hexDigits.join('')}</span></span>`);
    } else if (type === 'octal') {
        steps.push(`<span class="step">Step 1: Start with decimal value: <span class="highlight">${d}</span></span>`);
        let n = d;
        let octDigits = [];
        while (n > 0) {
            octDigits.push(n % 8);
            n = Math.floor(n / 8);
        }
        if (octDigits.length === 0) octDigits.push(0);
        octDigits.reverse();
        steps.push(`<span class="step">Step 2: Repeatedly divide by 8 and collect remainders</span>`);
        let temp = d;
        let lines = [];
        while (temp > 0) {
            lines.push(`${temp} ÷ 8 = ${Math.floor(temp/8)} remainder ${temp % 8}`);
            temp = Math.floor(temp / 8);
        }
        lines.reverse();
        lines.forEach(l => steps.push(`<span class="step">&nbsp;&nbsp;${l}</span>`));
        steps.push(`<span class="step">Step 3: Read remainders bottom-to-top: <span class="highlight">${octDigits.join('')}</span></span>`);
    }

    document.getElementById('explanationText').innerHTML = steps.join('');
}
