```mermaid
---
title: IWebCanvas
---
classDiagram
    class IWebCanvas
    IWebCanvas : +getContext(type) IWebCanvasContext
    
    IWebCanvas <|-- IWebCanvasOnScreen
    IWebCanvasOnScreen : +getContext(type) IWebCanvasContextOnscreen

    IWebCanvas <|-- IWebCanvasOffScreen
    IWebCanvasOffScreen : +getContext(type) IWebCanvasContextOffscreen
```
```mermaid
---
title: IWebCanvasContext
---
classDiagram
    class IWebCanvasContext {
        +IWebCanvas canvas
    }
    class IWebCanvasContextOnscreen {
        ~RenderExecutor renderExecutor
        ~startRender()
        ~stopRender()
        ~post(Runnable task)
        ~postDelayed(long delay, Runnable task)
        ~remove(Runnable task)
        ~postInvalidate()
    }
    class IWebCanvasContext2D {
        ~ICanvasPainter2D iCanvasPainter2D
    }
    class ICanvas2DProvider {
        ~Canvas canvas["android canvas"]
    }
    class IPainter2D {
        ~CanvasProvider provider
    }
    class RenderExecutor {
        ~setRenderMode(int mode)
    }
    class RenderStrategy {

    }
    

    IWebCanvasContext  <|-- IWebCanvasContextOnscreen
    IWebCanvasContext <|-- IWebCanvasContextOffscreen 
    IWebCanvasContext <|-- IWebCanvasContext2D 
    IPainter2D <|-- IWebCanvasContext2D
    IWebCanvasContext <|--  IWebCanvasContextWebGL 

    IWebCanvasContextOnscreen ..|> CanvasContextOnscreen2D
    IWebCanvasContext2D ..|> CanvasContextOnscreen2D

    IWebCanvasContextOnscreen ..|> CanvasContextOnscreenWebGL
    IWebCanvasContextWebGL ..|> CanvasContextOnscreenWebGL

    IWebCanvasContext2D ..|> CanvasContextOffscreen2D
    IWebCanvasContextOffscreen ..|> CanvasContextOffscreen2D
    

    IWebCanvasContextOffscreen ..|> CanvasContextOffscreenWebGL
    IWebCanvasContextWebGL ..|> CanvasContextOffscreenWebGL

    RenderExecutor *-- IWebCanvasContextOnscreen
    ICanvas2DProvider *-- IPainter2D
    RenderExecutor *-- RenderStrategy
```