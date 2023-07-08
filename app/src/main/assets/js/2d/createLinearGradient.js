function drawLinearGradient() {
    const gradient = ctx.createLinearGradient(20, 0, 220, 0);

    // Add three color stops
    gradient.addColorStop(0, "green");
    gradient.addColorStop(0.5, "cyan");
    gradient.addColorStop(1, "green");

    // Set the fill style and draw a rectangle
    ctx.fillStyle = gradient;
    ctx.fillRect(20, 20, 200, 100);

    const gradient1 = ctx.createLinearGradient(20, 0, 220, 0);
    gradient1.addColorStop(0, "blue");
    gradient1.addColorStop(0.5, "red");
    gradient1.addColorStop(1, "blue");

    ctx.fillStyle = gradient1;
    ctx.fillRect(20, 120, 200, 100);
}

drawLinearGradient();