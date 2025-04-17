package com.onthebrink.entity.util;

import com.onthebrink.entity.animal.base.AnimalBase;

public class AnimalDefinition {
    public String id;
    public String className;
    public String alias;
    public float width;
    public float height;
    public int hp;
    public int primary_color;
    public int secondary_color;

    public Class<? extends AnimalBase> getEntityClass() {
        try {
            Class<?> animalClass = Class.forName(className);
            if (!AnimalBase.class.isAssignableFrom(animalClass)){
                throw new RuntimeException("Class " + className + " is not an instance of AnimalBase");
            }
            return animalClass.asSubclass(AnimalBase.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load entity class: " + className, e);
        }
    }
}
