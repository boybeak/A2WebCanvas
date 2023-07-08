function arc2() {
    // Draw shapes
    for (let i = 0; i <= 3; i++) {
      for (let j = 0; j <= 2; j++) {
        ctx.beginPath();
        let x = 25 + j * 50; // x coordinate
        let y = 25 + i * 50; // y coordinate
        let radius = 20; // Arc radius
        let startAngle = 0; // Starting point on circle
        let endAngle = Math.PI + (Math.PI * j) / 2; // End point on circle
        let counterclockwise = i % 2 === 1; // Draw counterclockwise

        ctx.arc(x, y, radius, startAngle, endAngle, counterclockwise);

        if (i > 1) {
          ctx.fill();
        } else {
          ctx.stroke();
        }
      }
    }
}
arc2();