package com.mikedeejay2.apcspfinal.world;

public class WorldLightColor
{
    float skyColorR, skyColorG, skyColorB;
    float sunColorR, sunColorG, sunColorB;

    public WorldLightColor(float skyColorR, float skyColorG, float skyColorB, float sunColorR, float sunColorG, float sunColorB)
    {
        this.skyColorR = skyColorR;
        this.skyColorG = skyColorG;
        this.skyColorB = skyColorB;
        this.sunColorR = sunColorR;
        this.sunColorG = sunColorG;
        this.sunColorB = sunColorB;
    }

    public float getSkyColorR()
    {
        return skyColorR;
    }

    public void setSkyColorR(float skyColorR)
    {
        this.skyColorR = skyColorR;
    }

    public float getSkyColorG()
    {
        return skyColorG;
    }

    public void setSkyColorG(float skyColorG)
    {
        this.skyColorG = skyColorG;
    }

    public float getSkyColorB()
    {
        return skyColorB;
    }

    public void setSkyColorB(float skyColorB)
    {
        this.skyColorB = skyColorB;
    }

    public float getSunColorR()
    {
        return sunColorR;
    }

    public void setSunColorR(float sunColorR)
    {
        this.sunColorR = sunColorR;
    }

    public float getSunColorG()
    {
        return sunColorG;
    }

    public void setSunColorG(float sunColorG)
    {
        this.sunColorG = sunColorG;
    }

    public float getSunColorB()
    {
        return sunColorB;
    }

    public void setSunColorB(float sunColorB)
    {
        this.sunColorB = sunColorB;
    }
}
