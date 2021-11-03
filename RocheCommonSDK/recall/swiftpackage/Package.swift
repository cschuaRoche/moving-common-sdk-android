// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "AppRecall",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: "AppRecall",
            targets: ["AppRecall"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "AppRecall",
            path: "./AppRecall.xcframework"
        ),
    ]
)
