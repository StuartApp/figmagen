import SwiftUI

public let lightColors: Colors =
    Colors(
        contrast: lightContrast,
        primary: lightPrimary,
        surface: lightSurface
    )

private let lightPrimary: Colors.Primary =
    Colors.Primary(
        main: lightPrimaryMain,
        nested: lightPrimaryNested,
        soft: lightPrimarySoft
    )

private var lightPrimarySoft: Color {
    return Color(
        red: 0.8,
        green: 0.87058824,
        blue: 1.0,
        opacity: 1.0
    )
}

private var lightPrimaryMain: Color {
    return Color(
        red: 0.0,
        green: 0.35686275,
        blue: 1.0,
        opacity: 1.0
    )
}

private let lightPrimaryNested: Colors.Primary.Nested =
    Colors.Primary.Nested(
        main: lightPrimaryNestedMain
    )

private var lightPrimaryNestedMain: Color {
    return Color(
        red: 0.525,
        green: 0.7435,
        blue: 1.0,
        opacity: 1.0
    )
}

private let lightSurface: Colors.Surface =
    Colors.Surface(
        contrast: lightSurfaceContrast,
        main: lightSurfaceMain
    )

private var lightSurfaceContrast: Color {
    return Color(
        red: 0.21568628,
        green: 0.25490198,
        blue: 0.31764707,
        opacity: 1.0
    )
}

private var lightSurfaceMain: Color {
    return Color(
        red: 1.0,
        green: 1.0,
        blue: 1.0,
        opacity: 1.0
    )
}

private let lightContrast: Colors.Contrast =
    Colors.Contrast(
        main: lightContrastMain,
        soft: lightContrastSoft
    )

private var lightContrastMain: Color {
    return Color(
        red: 1.0,
        green: 1.0,
        blue: 1.0,
        opacity: 1.0
    )
}

private var lightContrastSoft: Color {
    return Color(
        red: 0.8509804,
        green: 0.8509804,
        blue: 0.8509804,
        opacity: 1.0
    )
}

public let darkColors: Colors =
    Colors(
        contrast: darkContrast,
        primary: darkPrimary,
        surface: darkSurface
    )

private let darkPrimary: Colors.Primary =
    Colors.Primary(
        main: darkPrimaryMain,
        nested: darkPrimaryNested,
        soft: darkPrimarySoft
    )

private let darkPrimaryNested: Colors.Primary.Nested =
    Colors.Primary.Nested(
        main: darkPrimaryNestedMain
    )

private var darkPrimaryNestedMain: Color {
    return Color(
        red: 1.0,
        green: 0.714,
        blue: 0.45,
        opacity: 1.0
    )
}

private var darkPrimarySoft: Color {
    return Color(
        red: 1.0,
        green: 0.908,
        blue: 0.8,
        opacity: 1.0
    )
}

private var darkPrimaryMain: Color {
    return Color(
        red: 1.0,
        green: 0.54,
        blue: 0.0,
        opacity: 1.0
    )
}

private let darkSurface: Colors.Surface =
    Colors.Surface(
        contrast: darkSurfaceContrast,
        main: darkSurfaceMain
    )

private var darkSurfaceContrast: Color {
    return Color(
        red: 0.92083335,
        green: 0.92083335,
        blue: 0.92083335,
        opacity: 1.0
    )
}

private var darkSurfaceMain: Color {
    return Color(
        red: 0.16666667,
        green: 0.16666667,
        blue: 0.16666667,
        opacity: 1.0
    )
}

private let darkContrast: Colors.Contrast =
    Colors.Contrast(
        main: darkContrastMain,
        soft: darkContrastSoft
    )

private var darkContrastMain: Color {
    return Color(
        red: 0.20833333,
        green: 0.20833333,
        blue: 0.20833333,
        opacity: 1.0
    )
}

private var darkContrastSoft: Color {
    return Color(
        red: 0.6375,
        green: 0.6375,
        blue: 0.6375,
        opacity: 1.0
    )
}

public struct Colors {

    public let contrast: Contrast

    public let primary: Primary

    public let surface: Surface

    public struct Primary {

        public let main: Color

        public let nested: Nested

        public let soft: Color

        public struct Nested {

            public let main: Color

        }
    }

    public struct Surface {

        public let contrast: Color

        public let main: Color

    }

    public struct Contrast {

        public let main: Color

        public let soft: Color

    }
}
