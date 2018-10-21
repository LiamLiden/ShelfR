# ShelfR
HACKISU 2018 Submission
## Inspiration
You might not be too surprised to learn that about 1 billion people across the world go hungry every day. However, many people would be surprised to know that the reason for this is not a shortage of food, it's simply due to cumulative human errors. And although many people do not like talking about it, these cumulative errors add up to a shocking _30% of produced food_ that goes straight to the landfill. Clearly, food waste is a major problem across the world. 

You might be even more surprised to learn that a large part of this aforementioned human error are the acts of everyday people throwing away food because they bought too much, and whatever they could not use in time ended up spoiling. This not only causes direct harm by wasting food that could have fed a hungry person, but also leads to the depletion of valuable resources that could have been used for something better.

In the end, no one wants to be the person that has to throw away their extra food. Not only does it feel selfish, but it also wastes money. Therefore, we developed an app that helps everyday people manage their food purchases and stop unknowingly contributing to one of the greatest problems our world faces today. 
## What it does
Our application gives people a simple way to keep track of their food purchases and the expiration status of their food. Given a product a user might wish to buy, the app searches for the product's expiration date and sets up a calendar/notification to alert users if they haven't used up their food as the expiration date gets closer. After the user has trained the app with sufficient data based on their average rate of consumption of certain products, the app is also designed to make intelligent suggestions on how much of a certain product a user should buy to avoid over purchasing. The app also maintains a convenient and editable inventory of food items in the user's house, letting them check at a glance what they already have in their home to avoid buying items before they run out.  
## How we built it
We built this application in Android Studio. We parsed a database of expiration dates for common food items and provided users this information. We built a search function that helps users navigate the data available and saved the results the users wanted. We then take the saved data and apply it in various ways to provide notification and prediction functionalities.
## Challenges we ran into
One of the biggest challenges we ran into was the proper storing of application data to maintain the user's entry. We also ran into difficulty parsing our dataset, as it wasn't exactly machine readable to start with. 
## Accomplishments that we're proud of
We're very proud of the fact that we managed to churn out a functional app with (almost) all of the features we initially targeted. Although we started out rough, we were able to link everything seamlessly in the end and produce an app we are quite proud of. We also managed to use Gson at some point, which was pretty cool.
## What we learned
Throughout the process, we learned a lot about Android development and Android Studio. For example, we deeply explored how notifications work and the process of sending and receiving broadcast signals. We also learned a lot about different types of layouts and views and how to link functions together. Finally, we learned how to use Git properly for complex group projects.
## What's next for ShelfR
Next, we want to test our statistics function with pseudo-data to ensure it can predict the appropriate amount of certain types of food users should purchase. We also hope to increase the size of our database and more effectively match the user's input. It would also be interesting to try and combine a QR/barcode scanner into our app that can automatically register the item being purchased as well as its expiration date.
