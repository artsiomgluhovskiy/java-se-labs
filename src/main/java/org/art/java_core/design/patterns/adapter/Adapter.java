package org.art.java_core.design.patterns.adapter;

/**
 * Adapter pattern (from GoF) - simple code example.
 * Simple vector-raster graphic adapter implementation.
 */
public class Adapter {

    public static void main(String[] args) {

        //1 using composition
        VectorGraphicsIF vectorGraphic2 = new VectorAdapterFromRaster2(new RasterGraphics());
        vectorGraphic2.drawVectorLine();
        vectorGraphic2.drawVectorSquare();

        //2 using inheritance
        VectorGraphicsIF vectorGraphic1 = new VectorAdapterFromRaster();
        vectorGraphic1.drawVectorLine();
        vectorGraphic1.drawVectorSquare();
    }
}

interface VectorGraphicsIF {

    void drawVectorLine();
    void drawVectorSquare();
}

class RasterGraphics {

    void drawRasterLine() {
        System.out.println("Draw raster line...");
    }

    void drawRasterSquare() {
        System.out.println("Draw raster square...");
    }
}

class VectorAdapterFromRaster extends RasterGraphics implements VectorGraphicsIF {

    @Override
    public void drawVectorLine() {
        drawRasterLine();
    }

    @Override
    public void drawVectorSquare() {
        drawRasterSquare();
    }
}

class VectorAdapterFromRaster2 implements VectorGraphicsIF {

    private RasterGraphics rg;

    public VectorAdapterFromRaster2(RasterGraphics raster) {
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
