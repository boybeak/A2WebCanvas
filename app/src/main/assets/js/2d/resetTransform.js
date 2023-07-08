function drawResetTransform() {
    // Draw a rotated rectangle
    ctx.rotate((45 * Math.PI) / 180);
    ctx.fillRect(60, 0, 100, 30);

    // Reset transformation matrix to the identity matrix
    ctx.resetTransform();
}
drawResetTransform();