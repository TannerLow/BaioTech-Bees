package io.github.TannerLow.baiotechbees.items.util;

import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtShort;
import net.minecraft.nbt.NbtString;

import java.util.Random;

public class Gene {
    public enum Type {
        STRING,
        SHORT,
        BYTE,
        INT,
        DOUBLE
    }

    public String name;
    public Type type;
    public Object value1;
    public Object value2;

    public Gene(String name, Type type, Object value1, Object value2) {
        this.name = name;
        this.type = type;
        this.value1 = value1;
        this.value2 = value2;
    }

    public Gene(NbtCompound nbt) {
        readNbt(nbt);
    }

    public void writeNbt(NbtCompound nbt) {
        nbt.putByte("Type", serializeType(type));
        nbt.putString("Name", name);

        NbtList genePair = new NbtList();
        if(type == Type.STRING) {
            genePair.add(new NbtString((String)value1));
            genePair.add(new NbtString((String)value2));
        }
        else if(type == Type.SHORT) {
            genePair.add(new NbtShort((short)value1));
            genePair.add(new NbtShort((short)value2));
        }
        else if(type == Type.BYTE) {
            genePair.add(new NbtByte((byte)value1));
            genePair.add(new NbtByte((byte)value2));
        }
        else if(type == Type.INT) {
            genePair.add(new NbtInt((int)value1));
            genePair.add(new NbtInt((int)value2));
        }
        else if(type == Type.DOUBLE) {
            genePair.add(new NbtDouble((double)value1));
            genePair.add(new NbtDouble((double)value2));
        }

        nbt.put("GenePair", genePair);
    }

    public void readNbt(NbtCompound nbt) {
        type = deserializeType(nbt.getByte("Type"));
        name = nbt.getString("Name");

        NbtList list = nbt.getList("GenePair");
        if(type == Type.STRING) {
            value1 = ((NbtString)list.get(0)).value;
            value2 = ((NbtString)list.get(1)).value;
        }
        else if(type == Type.SHORT) {
            value1 = ((NbtShort)list.get(0)).value;
            value2 = ((NbtShort)list.get(1)).value;
        }
        else if(type == Type.BYTE) {
            value1 = ((NbtByte)list.get(0)).value;
            value2 = ((NbtByte)list.get(1)).value;
        }
        else if(type == Type.INT) {
            value1 = ((NbtInt)list.get(0)).value;
            value2 = ((NbtInt)list.get(1)).value;
        }
        else if(type == Type.DOUBLE) {
            value1 = ((NbtDouble)list.get(0)).value;
            value2 = ((NbtDouble)list.get(1)).value;
        }
    }

    public Gene copy() {
        return new Gene(name, type, value1, value2);
    }

//    public Object getAlele(final Random rng) {
//        return rng.nextBoolean() ? value1 : value2;
//    }

//    public Gene rename(String newName) {
//        return new Gene(newName, type, value1, value2);
//    }

    public static byte serializeType(Type type) {
        switch(type) {
            case STRING: return 1;
            case SHORT: return 2;
            case BYTE: return 3;
            case INT: return 4;
            case DOUBLE: return 5;
            default: return 0;
        }
    }

    public static Type deserializeType(byte typeAsByte) {
        switch(typeAsByte) {
            case 1: return Type.STRING;
            case 2: return Type.SHORT;
            case 3: return Type.BYTE;
            case 4: return Type.INT;
            case 5: return Type.DOUBLE;
            default: throw new RuntimeException("Invalid NBT value for Type encountered during deserialization of Gene");
        }
    }

    @Override
    public String toString() {
        String str = "Name: " + name + " | Values: ";
        str += value1.toString() + ", ";
        str += value2.toString();
        return str;
    }

    public static void print(NbtCompound nbt) {
        Gene gene = new Gene(nbt);
        System.out.println(gene);
    }
}
