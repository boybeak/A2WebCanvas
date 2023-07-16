function drawArc() {
    Console.log('drawArc start');
    ctx.beginPath();
    ctx.strokeStyle = 'red';
    ctx.arc(100, 75, 50, 0, 2 * Math.PI);
    ctx.stroke();
    Console.log('drawArc end');
}
drawArc();