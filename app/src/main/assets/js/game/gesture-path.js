const point = {x: 0, y: 0, isTouching: false};
function drawTouch() {
    if (point.isTouching) {
        ctx.fillStyle = 'red';
        ctx.beginPath();
        ctx.arc(point.x, point.y, 10, 0, 2 * Math.PI);
        ctx.fill();
    }
    window.requestAnimationFrame(drawTouch);
}
drawTouch();
function onTouchStart(x, y) {
    point.x = x;
    point.y = y;
    point.isTouching = true
}
function onTouchMove(x, y) {
    point.x = x;
    point.y = y;
}
function onTouchEnd(x, y) {
    point.x = x;
    point.y = y;
    point.isTouching = false
    Console.log('onTouchEnd - (', x, ', ', y, ')');
}