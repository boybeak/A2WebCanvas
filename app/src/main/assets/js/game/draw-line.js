var currentPath = [];

function drawPath() {
    if (currentPath.length > 0) {
        ctx.beginPath();
        ctx.lineJoin = "round";
        ctx.lineCap = "round";
        ctx.strokeStyle = 'red';
        ctx.lineWidth = 8;
        for (let i = currentPath.length -1; i >= 0; i--) {
            let p = currentPath[i];
            if (i == currentPath.length -1) {
                ctx.moveTo(p.x, p.y);
            } else {
                ctx.lineTo(p.x, p.y);
            }
        }
        ctx.stroke();
    }
    window.requestAnimationFrame(drawPath);
}
drawPath();

function onTouchStart(x, y) {
    currentPath.push({x: x, y: y});
}
function onTouchMove(x, y) {
    currentPath.push({x: x, y: y});
}
function onTouchEnd(x, y) {
    currentPath = [];
}