# HMS-SecureWebBrowserExample
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)<br/><br/>

## Contents
1. [Introduction](#introduction)
2. [Used third party libraries](#used-third-party-libraries)
   - [Koin](#koin)
   - [Lottie](#lottie)
3. [Huawei Mobile Services](#huawei-mobile-services)
   - [Checking is HMS available on device](#checking-is-hms-available-on-device)
   - [Safety Detect](#safety-detect)
     - [SysIntegrity API](#sysintegrity-api)
     - [AppsCheck API](#appscheck-api)

## Introduction
This repository is a reference application for explaining the usage of some of Huawei Mobile Services.<br/>
In this application, I am using some of Huawei Mobile Services and I am explaining how to use these services in your android application.<br/><br/>

Unlike the web browsers which we are using, I check the users' devices to be secure. Because, if we are doing something on internet, we should be care about our device security.<br/>
Because in web browsers, we can use bank websites, we can login to our social media, we can make some payment and use our credit/bank card information. We wouldn't like to our information to be stolen.<br/>
Our duty as software engineers are developing applications to be secure. Thus, users can do their operations as freely and they won't worry about their security.<br/><br/>

<b>Note:</b> For now, I only developed the parts which are checking users device to be secure and checking that device is supporting HMS or is not.

## Used third party libraries
I used a few third party libraries while developing this application. I will give brief descriptions about this libraries.
### Koin
Koin is a dependency injection framework. It has written in Kotlin and it is really lightweight framework. <br/>
It gives many features for dependency injection and you can implement necessary things so easily.
### Lottie
Lottie is an animation library which was created by Airbnb. You can add different animations easily with a json file.<br/>
With Lottie, you can create modern user interfaces. It will give your app better look.

## Huawei Mobile Services
Huawei Mobile Services(HMS) is an alternative to Google Mobile Services. HMS is developing by Huawei and giving new option for the mobile application development ecosystem. Before that, developers were developing applications by using GMS and Firebase. <br/>
Now Huawei devices are not supporting GMS. That's why developers need to learn HMS while developing Android applications. Because there are two app marktes out there and developers should focus on both app markets.<br/><br/>
If you would like to learn more about Huawei Mobile Services, you can visit the developer website:<br/>
https://developer.huawei.com/consumer/en <br/>
If you would like to learn about use cases, implementation example and more informations about HMS, you can visit Huawei Developer Medium page:<br/>
https://medium.com/huawei-developers <br/><br/>

I will give brief information about each service and then I will add codes about the service to show that how you can use the service.

### Checking is HMS available on device
This section is optional. Normally, you don't have to check that is hms available on device or is not. But if application is dependent to HMS so much and couldn't work without it, it will be better to check that device has HMS support or has not.<br/>
Thus, users will not be able to run the application and user will not have any problem because of HMS availability.<br/><br/>
Firstly, we need to add base HMS dependency to our app-level build.gradle file.<br/>
We can use following version and following dependency.
```gradle
def hmsBaseVersion = "5.0.3.300"

dependencies {
    ...
    // HMS Base
    implementation "com.huawei.hms:base:${hmsBaseVersion}"
}
```
To check that device has HMS support or has not, we can write very basic function.
```kotlin
class HmsHelper: KoinComponent{
    private val appContext: Context by inject(named("appContext"))

    fun isHmsAvailable(): Boolean {
        val isAvailable = HuaweiApiAvailability.getInstance().isHuaweiMobileNoticeAvailable(appContext)
        return (ConnectionResult.SUCCESS == isAvailable)
    }
}
```
If this function returns true, that means device has HMS support and we can start our application.<br/>
If this function returns false, that means device doesn't have HMS support and we shouldn't start our application. We may show an error screen to user.<br/>
<img src="https://raw.githubusercontent.com/berkberberr/HMS-SecureWebBrowserExample/main/screenshots/HMSUnavailable.jpg" width="216" height="462">

### Safety Detect
With the help of Safety Detect, we can improve the security of our application. It helps us to detect malicious apps on device, checking that device is secure or is not, determining that url has threat or has not, checking that user is fake or is not, checking that connected Wi-Fi is secure or is not.
#### SysIntegrity API
SysIntegrity API helps us to check that the user's device is secure or is not. Even if the device has been rooted, SysIntegrity API will tell us that device is not secure.<br/><br/>
To check the device security, we can call our isDeviceSecure() function.<br/>
As you see, this function will create a nonce value with an algorithm and pass this value to checkDeviceSecurity() function.<br/>
You may ask that, what is the algorithm value which I have used as "Constants.SAFETY_DETECT_ALGORITHM". You can define this algorithm value as shown in below:
```kotlin
object Constants{
    const val BASIC_INTEGRITY = "basicIntegrity"
    const val SAFETY_DETECT_ALGORITHM = "SHA1PRNG"
}
```
As you see, we have defined two different values. We will use these values while checking device security.<br/>
You already know where to use SAFETY_DETECT_ALGORITHM value.<br/>
We will use BASIC_INTEGRITY value to get device security situation from JSON.<br/>
If this value returns true, that means user's device is secure.<br/>
If this value returns false, that means device is not secure or device has been rooted.
```kotlin
object SafetyDetectService : KoinComponent {
    private val appContext: Context by inject(named("appContext"))
    private val client: SafetyDetectClient = SafetyDetect.getClient(appContext)

    fun isDeviceSecure(serviceListener: IServiceListener<Boolean>) {
        val nonce = ByteArray(24)
        try {
            val random: SecureRandom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                SecureRandom.getInstanceStrong()
            else
                SecureRandom.getInstance(Constants.SAFETY_DETECT_ALGORITHM)
            random.nextBytes(nonce)
        } catch (error: NoSuchAlgorithmException) {
            serviceListener.onError(ErrorType.NO_SUCH_OBJECT)
        }

        checkDeviceSecurity(nonce, serviceListener)
    }

    private fun checkDeviceSecurity(nonce: ByteArray, serviceListener: IServiceListener<Boolean>){
        client.sysIntegrity(nonce, BuildConfig.APP_ID)
            .addOnSuccessListener { sysIntegrityResp ->
                SafetyDetectHelper.getPayloadDetailAsJson(sysIntegrityResp)?.let { jsonObject ->
                    serviceListener.onSuccess(jsonObject.getBoolean(Constants.BASIC_INTEGRITY))
                } ?: kotlin.run {
                    serviceListener.onError(ErrorType.SERVICE_FAILURE)
                }
            }
            .addOnFailureListener {
                serviceListener.onError(ErrorType.SERVICE_FAILURE)
            }
    }
}
```
As I talked about above, we need to get a json object from SysIntegrityResp object which has been returned by SysIntegrity API. To get this value, we can define a helper object and we can add all operations about getting json in here.<br/>
As you see in the below, we will send a SysIntegrityResp object as parameter and with the help of this function, we can get json object about our device security.
```kotlin
object SafetyDetectHelper {
    fun getPayloadDetailAsJson(sysIntegrityResp: SysIntegrityResp): JSONObject? {
        val jwsStr = sysIntegrityResp.result
        val jwsSplit = jwsStr.split(".").toTypedArray()
        val jwsPayloadStr = jwsSplit[1]
        val payloadDetail = String(Base64.decode(
            jwsPayloadStr.toByteArray(StandardCharsets.UTF_8), Base64.URL_SAFE),
            StandardCharsets.UTF_8)

        return try {
            JSONObject(payloadDetail)
        }catch (jsonError: JSONException){
            null
        }
    }
}
```
If device is secure, we can do our next operations which we need to do. If device is not secure, we should show an error screen to user and we shouldn't let user to start our application.<br/>
<img src="https://raw.githubusercontent.com/berkberberr/HMS-SecureWebBrowserExample/main/screenshots/DeviceNotSecure.jpg" width="216" height="462">
#### AppsCheck API
AppsCheck API helps us to determine malicious apps in user's device. Thus, if device has some malicious apps, we will not let user to start our application for user's security.<br/><br/>
getMaliciousAppsList() function gives us a list of malicious app and it uses MaliciousAppsData class which has been defined by Huawei as a model class.<br/>
This API will return us a response object and this object will have the malicious apps list. If there is not any malicious apps on the device, we can return null and let user to use our application.<br/>
But if there are some malicious apps, we shouldn't let user to start our application and we can show an error screen to user. If we would like to we can list malicious apps to user.<br/><br/>
<b>Note:</b> It is better to list malicious apps and let user to delete these applications from device. That is what I am doing in my app. Also, if we would like to do more operations about malicious apps, we can define our own class like I talked about below.
```kotlin
object SafetyDetectService : KoinComponent {
    private val appContext: Context by inject(named("appContext"))
    private val client: SafetyDetectClient = SafetyDetect.getClient(appContext)

    fun checkMaliciousApps(serviceListener: IServiceListener<ArrayList<MaliciousApps>?>){
        client.maliciousAppsList
            .addOnSuccessListener { maliciousAppsListResp ->
                if(maliciousAppsListResp.rtnCode == CommonCode.OK){
                    val maliciousAppsList: List<MaliciousAppsData> = maliciousAppsListResp.maliciousAppsList
                    if(maliciousAppsList.isEmpty())
                        serviceListener.onSuccess(null)
                    else{
                        var maliciousApps = arrayListOf<MaliciousApps>()
                        for(maliciousApp in maliciousAppsList){
                            maliciousApp.apply {
                                maliciousApps.add(MaliciousApps(packageName = apkPackageName,
                                    sha256 = apkSha256,
                                    apkCategory = apkCategory))
                            }
                        }
                        serviceListener.onSuccess(maliciousApps)
                    }
                }
            }
            .addOnFailureListener {
                serviceListener.onError(ErrorType.SERVICE_FAILURE)
            }
    }
}
```
If we would like to do more operations like getting app icon, app name and etc. we can define our own data class.<br/>
I defined my own data class as shown in the below to do more specific operations with malicious apps.
```kotlin
data class MaliciousApps(
    val packageName: String,
    val sha256: String,
    val apkCategory: Int
): KoinComponent{
    private val appContext: Context = get(named("appContext"))
    private val systemHelper: SystemHelper = get()

    fun getAppIcon(): Drawable = systemHelper.getAppIconByPackageName(packageName)
    fun getAppName(): String = systemHelper.getAppNameByPackageName(packageName)

    fun getThreatDescription(): String {
        return when(apkCategory){
            1 -> appContext.getString(R.string.risky_app_description)
            2 -> appContext.getString(R.string.virus_app_description)
            else -> ""
        }
    }
}
```
Here I am just using same values with Huawei's MaliciousAppsData class. But I added my own functions in here to get app icon, app name and threat description.<br/>
To get more information about application by package name, we can define new object called as SystemHelper and we can do these operations in here.
```kotlin
class SystemHelper: KoinComponent {
    private val appContext: Context by inject(named("appContext"))

    /**
     * Getting application information by package name
     * @param packageName: Package name of the app that we want to get information about
     * @return ApplicationInfo class to get app icons, app names and etc. by package name
     */
    private fun getAppByPackageName(packageName: String): ApplicationInfo{
        return appContext.packageManager.getApplicationInfo(packageName, 0)
    }

    /**
     * Getting application icon by package name
     * @param packageName: Package name of the app which we want to get icon
     * @return Icon of the application as drawable
     */
    fun getAppIconByPackageName(packageName: String): Drawable{
        val app = getAppByPackageName(packageName)
        return appContext.packageManager.getApplicationIcon(app)
    }

    /**
     * Getting application name by package name
     * @param packageName: Package name of the app which we want to get name
     * @return Name of the application as drawable
     */
    fun getAppNameByPackageName(packageName: String): String{
        val app = getAppByPackageName(packageName)
        return appContext.packageManager.getApplicationLabel(app).toString()
    }
}
```
<img src="https://raw.githubusercontent.com/berkberberr/HMS-SecureWebBrowserExample/main/screenshots/ListMaliciousApps.jpg" width="216" height="462"> <img src="https://raw.githubusercontent.com/berkberberr/HMS-SecureWebBrowserExample/main/screenshots/DeleteMaliciousApp.jpg" width="216" height="462">
