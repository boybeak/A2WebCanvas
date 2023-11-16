function clip() {
    // Create circular clipping region
    ctx.beginPath();
    ctx.arc(100, 75, 50, 0, Math.PI * 2);
    ctx.clip();

    // Draw stuff that gets clipped
    ctx.fillStyle = "blue";
    ctx.fillRect(0, 0, 300, 150);
    ctx.fillStyle = "orange";
    ctx.fillRect(0, 0, 100, 100);
}
clip();