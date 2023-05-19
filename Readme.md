### Listed Application - Assignment developed in 30 HRs

# Please find below doc for this assignment to understand work flor

1. This project is developed in clean MVVM architecture
2. Used Android Security Crypto lib for Encryption the [API TOKEN]
3. Used Retrofit lib for API integration
4. Used Piccasso lib for Image loading
5. Used Dagger lib for Dependency Injection
6. For Graphs used MPAndroidChart lib

# View 
1. Dashboard is showing all the data which we parse from API
2. [Link Adaptor] is common for top and recent links
3. View are loosely couple with ViewModel (means less chance to get error and crash)

# ViewModel (Mediator)
1. MainActivity View Model is gathering data from remote/model and send to there subscribers
2. Greeting, List of link and api data

# Model (Remote)
1. Integrate api with Retrofit and fetch data
2. Send data to mediator who will be responsible for extract data and forward to its subscribers

----------------

# Security
- Token data is encrypted and no chance to hack it

# Benefits
1. I follow clean MVVM arch which is most robust than other arch
2. This project is loosely coupled, so less chance of bug and crash
3. Dagger is used for DI
4. Secret info saved in encrypted form

# Some more IMP class
1. [utils/DateUtils]
2. [utils/Extensions]
3. [utils.Listeners]
4. [MyApplication]
5. [module/build.gradle] for dependencies