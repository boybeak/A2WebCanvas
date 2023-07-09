const snake = [];
let xRectCount = 0;
let yRectCount = 0;
let rectSize = 10;
let moveDirection = {x: 1, y: 0};
let food = {x: 0, y: 0};

function prepare() {
    xRectCount = ctx.width / rectSize;
    yRectCount = ctx.height / rectSize;

    let startX = Math.floor(Math.random() * xRectCount) * rectSize;
    let startY = Math.floor(Math.random() * yRectCount) * rectSize;

    snake.push({x: startX, y: startY});

    randomFood();

    drawScene();
}
prepare();

var drawCounter = 0;
var hit = false;
function drawScene() {
    if (snake.length > 0) {
        if (drawCounter % 40 == 0) {
            let tail = snake[snake.length - 1];

            let header = snake[0]
            tail.x = header.x + rectSize * moveDirection.x;
            tail.y = header.y + rectSize * moveDirection.y;

            if (tail.x >= ctx.width) {
                tail.x = 0;
            } else if (tail.x < 0) {
                tail.x = ctx.width - rectSize;
            }

            if (tail.y >= ctx.height) {
                tail.y = 0;
            } else if (header.y < 0) {
                tail.y = ctx.height - rectSize;
            }
            snake.splice(0, 0, snake.pop());
            hit = checkHit();
            if (hit) {
                let newTail = {x: tail.x, y: tail.y};
                snake.push(newTail);

                randomFood();
            }



            drawCounter = 0;
        }
        drawSnake();
        if (drawCounter < 32) {
            drawFood();
        }

        drawCounter++;
    }
    window.requestAnimationFrame(drawScene);
}
function drawSnake() {
    ctx.fillStyle = 'red';
    for(let i = 0; i < snake.length; i++) {
        const rect = snake[i];
        ctx.fillRect(rect.x, rect.y, rectSize, rectSize);
    }
}
function drawFood() {
    ctx.fillStyle = 'black';
    ctx.fillRect(food.x, food.y, rectSize, rectSize);
}
function randomFood() {
    food.x = Math.floor(Math.random() * xRectCount) * rectSize;
    food.y = Math.floor(Math.random() * yRectCount) * rectSize;
}
function checkHit() {
    let header = snake[0];
    return header.x == food.x && header.y == food.y;
}

var startX = 0;
var startY = 0;
function onTouchStart(x, y) {
    startX = x;
    startY = y;
}
function onTouchMove(x, y) {
}
function onTouchEnd(x, y) {
    const deltaX = x - startX
    const deltaY = y - startY
    const absDeltaX = Math.abs(deltaX)
    const absDeltaY = Math.abs(deltaY)
    if (absDeltaX > absDeltaY) {
        moveDirection.x = deltaX / absDeltaX;
        moveDirection.y = 0
    } else {
        moveDirection.y = deltaY / absDeltaY;
        moveDirection.x = 0
    }
}