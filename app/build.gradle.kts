plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.drodx"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.drodx"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}



dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    //Rounded ImageView
    implementation("com.makeramen:roundedimageview:2.3.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    //Circle ImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //maps
    implementation ("com.google.android.gms:play-services-maps:19.0.0")
    implementation ("com.google.android.gms:play-services-location:21.3.0")

    //messages
    implementation("com.google.firebase:firebase-messaging")

//    //Razor payment method
//    implementation ("com.razorpay:checkout:1.6.40")

    //icons
    implementation ("com.google.android.material:material:1.9.0")

    //PayHere
    implementation ("com.github.PayHereDevs:payhere-android-sdk:v3.0.17")
    implementation ("androidx.appcompat:appcompat:1.6.0") // ignore if you have already added
    implementation ("com.google.code.gson:gson:2.8.0") // ignore if you have already added

    //Pie Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //barchart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
}