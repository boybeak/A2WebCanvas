const dots = [];
function drawOneDot(e) {
    ctx.fillStyle = 'rgba(' + e.red + ', ' + e.green + ', ' + e.blue+ ', ' + e.alpha + ')';
    ctx.beginPath();
    ctx.arc(e.x, e.y, e.r, 0, 2 * Math.PI)
    ctx.fill();

    e.r -= 0.1;
    e.alpha -= 0.05;
    if (e.alpha < 0) {
        e.alpha = 0;
    }
    if (e.r < 0) {
        dots.splice(0, 1);
    }
}
function gameStart() {
    if (dots.length > 0) {
        dots.forEach(drawOneDot)
    }
    window.requestAnimationFrame(gameStart);
}
gameStart();
function onTouchStart(x, y) {
}
function onTouchMove(x, y) {
}
function onTouchEnd(x, y) {
    dots.push(newDot(x, y))
    Console.log('onTouchEnd - (', x, ', ', y, '), dots.length=', dots.length);
}
function newDot(x, y) {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return {x: x, y: y, r: 20, red: r, green: g, blue: b, alpha: 1};
}