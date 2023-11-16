function drawArcTo2() {
  ctx.beginPath();

  ctx.moveTo(20, 20);           // 创建开始点
  ctx.lineTo(100, 20);          // 创建水平线
  ctx.arcTo(150, 20, 150, 70, 50); // 创建弧

//  ctx.lineTo(150, 120);
//  ctx.arcTo(200, 120, 200, 170, 20); // 创建弧

  ctx.stroke();
}
drawArcTo2()