import Color from "color";

/**
 * Converts a color string or a color to a hex string.
 *
 * @param color The value to convert.
 *
 * @returns A hex string of the given color, including the alpha value.
 */
export const colorToHex = (color: string | Color | undefined): string | undefined => {

    if (!color) {
        return;
    }

    if (typeof color === "string") {
        color = new Color(color);
    }
    // Hex color values have no alpha component, so we have to add that explicitly.
    if (color.alpha() < 1) {
        let alpha = Math.round((color.alpha() * 255)).toString(16);
        if (alpha.length < 2) {
            alpha = "0" + alpha;
        }

        return color.hex() + alpha;
    } else {
        return color.hex();
    }
};