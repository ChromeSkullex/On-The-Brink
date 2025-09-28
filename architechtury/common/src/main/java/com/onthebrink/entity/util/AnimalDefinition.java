package com.onthebrink.entity.util;

import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class AnimalDefinition {
    public String id;
    public String className;
    public String alias;
    public float width;
    public float height;
    public int hp;
    public int primary_color;
    public int secondary_color;
    public Item spawnEggItem;
    public String diet;
    public String activity;
    public String command_item;
    //    Textures
    public ResourceLocation female_sleeping_texture;
    public ResourceLocation female_texture;
    public ResourceLocation male_sleeping_texture;
    public ResourceLocation male_texture;
    public ResourceLocation baby_texture;
    // Book Textures

    public ResourceLocation animal_book_image;
    public int textureWidth;
    public int textureHeight;


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
