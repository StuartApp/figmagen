import SwiftUI

public let lightColors: Colors =
    Colors(
        disabled: lightDisabled,
        primary: lightPrimary,
        secondary: lightSecondary,
        surface: lightSurface,
        tertiary: lightTertiary
    )

private let lightPrimary: Colors.Primary =
    Colors.Primary(
        contrast: lightPrimaryContrast,
        main: lightPrimaryMain,
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

private let lightPrimaryContrast: Colors.Primary.Contrast =
    Colors.Primary.Contrast(
        high: lightPrimaryContrastHigh,
        main: lightPrimaryContrastMain
    )

private var lightPrimaryContrastMain: Color {
    return Color(
        red: 1.0,
        green: 1.0,
        blue: 1.0,
        opacity: 1.0
    )
}

private var lightPrimaryContrastHigh: Color {
    return Color(
        red: 0.0,
        green: 1.0,
        blue: 0.0,
        opacity: 1.0
    )
}

private let lightSurface: Colors.Surface =
    Colors.Surface(
        contrast: lightSurfaceContrast,
        main: lightSurfaceMain,
        soft: lightSurfaceSoft
    )

private var lightSurfaceSoft: Color {
    return Color(
        red: 0.93333334,
        green: 0.96862745,
        blue: 1.0,
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

private let lightSurfaceContrast: Colors.Surface.Contrast =
    Colors.Surface.Contrast(
        high: lightSurfaceContrastHigh,
        main: lightSurfaceContrastMain
    )

private var lightSurfaceContrastMain: Color {
    return Color(
        red: 0.21568628,
        green: 0.25490198,
        blue: 0.31764707,
        opacity: 1.0
    )
}

private var lightSurfaceContrastHigh: Color {
    return Color(
        red: 0.5019608,
        green: 0.0,
        blue: 0.5019608,
        opacity: 1.0
    )
}

private let lightTertiary: Colors.Tertiary =
    Colors.Tertiary(
        main: lightTertiaryMain,
        soft: lightTertiarySoft
    )

private var lightTertiarySoft: Color {
    return Color(
        red: 1.0,
        green: 0.9764706,
        blue: 0.9137255,
        opacity: 1.0
    )
}

private var lightTertiaryMain: Color {
    return Color(
        red: 1.0,
        green: 0.7529412,
        blue: 0.18431373,
        opacity: 1.0
    )
}

private let lightSecondary: Colors.Secondary =
    Colors.Secondary(
        main: lightSecondaryMain,
        soft: lightSecondarySoft
    )

private var lightSecondarySoft: Color {
    return Color(
        red: 0.83137256,
        green: 0.9411765,
        blue: 0.92941177,
        opacity: 1.0
    )
}

private var lightSecondaryMain: Color {
    return Color(
        red: 0.23137255,
        green: 0.80784315,
        blue: 0.6745098,
        opacity: 1.0
    )
}

private let lightDisabled: Colors.Disabled =
    Colors.Disabled(
        main: lightDisabledMain,
        soft: lightDisabledSoft
    )

private var lightDisabledSoft: Color {
    return Color(
        red: 0.81960785,
        green: 0.8352941,
        blue: 0.85882354,
        opacity: 1.0
    )
}

private var lightDisabledMain: Color {
    return Color(
        red: 0.5921569,
        green: 0.6392157,
        blue: 0.6862745,
        opacity: 1.0
    )
}

public let darkColors: Colors =
    Colors(
        disabled: darkDisabled,
        primary: darkPrimary,
        secondary: darkSecondary,
        surface: darkSurface,
        tertiary: darkTertiary
    )

private let darkPrimary: Colors.Primary =
    Colors.Primary(
        contrast: darkPrimaryContrast,
        main: darkPrimaryMain,
        soft: darkPrimarySoft
    )

private let darkPrimaryContrast: Colors.Primary.Contrast =
    Colors.Primary.Contrast(
        high: darkPrimaryContrastHigh,
        main: darkPrimaryContrastMain
    )

private var darkPrimaryContrastMain: Color {
    return Color(
        red: 1.0,
        green: 1.0,
        blue: 1.0,
        opacity: 1.0
    )
}

private var darkPrimaryContrastHigh: Color {
    return Color(
        red: 0.0,
        green: 1.0,
        blue: 0.76,
        opacity: 1.0
    )
}

private var darkPrimarySoft: Color {
    return Color(
        red: 0.18350515,
        green: 0.2804982,
        blue: 0.4583185,
        opacity: 1.0
    )
}

private var darkPrimaryMain: Color {
    return Color(
        red: 0.15686275,
        green: 0.43529412,
        blue: 0.92941177,
        opacity: 1.0
    )
}

private let darkSurface: Colors.Surface =
    Colors.Surface(
        contrast: darkSurfaceContrast,
        main: darkSurfaceMain,
        soft: darkSurfaceSoft
    )

private let darkSurfaceContrast: Colors.Surface.Contrast =
    Colors.Surface.Contrast(
        high: darkSurfaceContrastHigh,
        main: darkSurfaceContrastMain
    )

private var darkSurfaceContrastMain: Color {
    return Color(
        red: 0.9585108,
        green: 0.9585108,
        blue: 0.9585108,
        opacity: 1.0
    )
}

private var darkSurfaceContrastHigh: Color {
    return Color(
        red: 0.37145114,
        green: 0.0,
        blue: 0.5019608,
        opacity: 1.0
    )
}

private var darkSurfaceMain: Color {
    return Color(
        red: 0.15415183,
        green: 0.15415183,
        blue: 0.15415183,
        opacity: 1.0
    )
}

private var darkSurfaceSoft: Color {
    return Color(
        red: 0.21568628,
        green: 0.24313726,
        blue: 0.27058825,
        opacity: 1.0
    )
}

private let darkTertiary: Colors.Tertiary =
    Colors.Tertiary(
        main: darkTertiaryMain,
        soft: darkTertiarySoft
    )

private var darkTertiaryMain: Color {
    return Color(
        red: 1.0,
        green: 0.7529412,
        blue: 0.18431373,
        opacity: 1.0
    )
}

private var darkTertiarySoft: Color {
    return Color(
        red: 0.5568628,
        green: 0.4862745,
        blue: 0.2901961,
        opacity: 1.0
    )
}

private let darkSecondary: Colors.Secondary =
    Colors.Secondary(
        main: darkSecondaryMain,
        soft: darkSecondarySoft
    )

private var darkSecondarySoft: Color {
    return Color(
        red: 0.37254903,
        green: 0.627451,
        blue: 0.6,
        opacity: 1.0
    )
}

private var darkSecondaryMain: Color {
    return Color(
        red: 0.23137255,
        green: 0.80784315,
        blue: 0.6745098,
        opacity: 1.0
    )
}

private let darkDisabled: Colors.Disabled =
    Colors.Disabled(
        main: darkDisabledMain,
        soft: darkDisabledSoft
    )

private var darkDisabledMain: Color {
    return Color(
        red: 0.3254902,
        green: 0.3529412,
        blue: 0.38431373,
        opacity: 1.0
    )
}

private var darkDisabledSoft: Color {
    return Color(
        red: 0.4392157,
        green: 0.4627451,
        blue: 0.5058824,
        opacity: 1.0
    )
}

public struct Colors {

    public let disabled: Disabled

    public let primary: Primary

    public let secondary: Secondary

    public let surface: Surface

    public let tertiary: Tertiary

    public struct Primary {

        public let contrast: Contrast

        public let main: Color

        public let soft: Color

        public struct Contrast {

            public let high: Color

            public let main: Color

        }
    }

    public struct Surface {

        public let contrast: Contrast

        public let main: Color

        public let soft: Color

        public struct Contrast {

            public let high: Color

            public let main: Color

        }
    }

    public struct Tertiary {

        public let main: Color

        public let soft: Color

    }

    public struct Secondary {

        public let main: Color

        public let soft: Color

    }

    public struct Disabled {

        public let main: Color

        public let soft: Color

    }
}
