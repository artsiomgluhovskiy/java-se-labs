package org.art.java_core.design.patterns.adapter;

/**
 * Adapter pattern (from GoF) - simple code example.
 * Simple vector-raster graphic adapter implementation:
 * - adapter model based on inheritance;
 * - adapter model based on composition.
 */
public class Adapter {

    public static void main(String[] args) {

        //1 using composition
        VectorGraphicsIF vectorGraphic2 = new RasterToVectorComposAdapter(new RasterGraphics());
        vectorGraphic2.drawVectorLine();
        vectorGraphic2.drawVectorSquare();

        //2 using inheritance
        VectorGraphicsIF vectorGraphic1 = new RasterToVectorInheritAdapter();
        vectorGraphic1.drawVectorLine();
        vectorGraphic1.drawVectorSquare();
    }
}

interface VectorGraphicsIF {

    void drawVectorLine();

    void drawVectorSquare();
}

interface RasterGraphicsIF {

    void drawRasterLine();

    void drawRasterSquare();
}

class RasterGraphics implements RasterGraphicsIF {

    @Override
    public void drawRasterLine() {
        System.out.println("Draw raster line...");
    }

    @Override
    public void drawRasterSquare() {
        System.out.println("Draw raster square...");
    }
}

//Adapter model based on inheritance
class RasterToVectorInheritAdapter extends RasterGraphics implements VectorGraphicsIF {

    @Override
    public void drawVectorLine() {
        drawRasterLine();
    }

    @Override
    public void drawVectorSquare() {
        drawRasterSquare();
    }
}

//Adapter model based on composition
class RasterToVectorComposAdapter implements VectorGraphicsIF {

    private RasterGraphics rg;

    public RasterToVectorComposAdapter(RasterGraphics raster) {
        this.rg = raster;
    }

    @Override
    public void drawVectorLine() {
        rg.drawRasterLine();
    }

    @Override
    public void drawVectorSquare() {
        rg.drawRasterSquare();
    }
}
