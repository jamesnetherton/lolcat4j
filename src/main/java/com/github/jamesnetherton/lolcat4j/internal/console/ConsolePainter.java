/*
 * #%L
 * lolcat4j
 * %%
 * Copyright (C) 2016 James Netherton
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package com.github.jamesnetherton.lolcat4j.internal.console;

import com.github.jamesnetherton.lolcat4j.Lol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class ConsolePainter {

    abstract void output(Lol lol, ColorSeed seed, String line);
    abstract void beforePainting();
    abstract void afterPainting();

    public void paint(Lol lol) throws IOException {
        try {
            InputStream inputStream = new ByteArrayInputStream(lol.getText().getBytes("UTF-8"));
            paint(lol, inputStream);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void paint(Lol lol, InputStream inputStream) throws IOException {
        final ColorSeed seed = new ColorSeed(lol.getSeed());

        beforePainting();
        try (Scanner scanner = new Scanner(inputStream)) {
            scanner.useDelimiter("(?<=\n)|(?!\n)(?<=\r)");
            while (scanner.hasNext()) {
                output(lol, seed, cleanString(scanner.next()));
            }
        }
        afterPainting();
    }

    String getHexRgbString(Lol lol, int seed, int i) {
        String rainbow = rainbow(lol.getFrequency(), seed + (i / lol.getSpread()));
        List<Integer> rgbValues = hexToRgb(rainbow);
        return rgb(rgbValues.get(0), rgbValues.get(1), rgbValues.get(2));
    }

    private String rainbow(double frequency, double i) {
        Double red = Math.sin(frequency * i + 0) * 127 + 128;
        Double green = Math.sin(frequency * i + 2 * Math.PI / 3) * 127 + 128;
        Double blue = Math.sin(frequency * i + 4 * Math.PI / 3) * 127 + 128;

        return String.format("#%02X%02X%02X", red.intValue(), green.intValue(), blue.intValue());
    }

    private List<Integer> hexToRgb(String hexString) {
        final String hexNoHash = hexString.replace("#", "");
        final List<Integer> rgb = new ArrayList<>();
        if (hexNoHash.length() == 6) {
            int start = 0;
            do {
                String hex = hexNoHash.substring(start, start + 2);
                rgb.add(Integer.parseInt(hex, 16));
                start += 2;
            } while (start <= hexNoHash.length() - 2);
        } else {
            for (int i = 0; i < hexNoHash.length(); i++) {
                rgb.add(Integer.parseInt(String.valueOf(hexString.charAt(i))) * 2, 16);
            }
        }

        return rgb;
    }

    private String rgb(int red, int green, int blue) {
        return "38" + rgbValue(red, green, blue);
    }

    private String rgbValue(int red, int green, int blue) {
        boolean grayPossible = true;
        boolean gray = false;
        double sep = 42.5;

        while (grayPossible) {
            if (red < sep || green < sep || blue < sep) {
                gray = red < sep && green < sep && blue < sep;
                grayPossible = false;
            }
            sep += 42.5;
        }

        if (gray) {
            int num = 232 + Math.round((float) red + (float) green + (float) blue / 33);
            return ";5;" + num;
        } else {
            List<Integer> rgbValues = new ArrayList<>();
            rgbValues.add(red);
            rgbValues.add(green);
            rgbValues.add(blue);

            List<Integer> grayValues = new ArrayList<>();
            grayValues.add(36);
            grayValues.add(6);
            grayValues.add(1);

            int val = 0;

            for (int i = 0; i < rgbValues.size(); i++) {
                int color = rgbValues.get(i);
                int mod = grayValues.get(i);
                val += (int) (6 * ((float) color / 256)) * mod;
            }

            return ";5;" + (val + 16);
        }
    }

    private String cleanString(String s) {
        return s.replaceAll("\\\\e\\[[\\d;]*[m|K]", "").replaceAll("\t", "        ");
    }
}
