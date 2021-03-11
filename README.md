# classified-ads

Classified ads project based on the [Spring framework](https://spring.io/) for MAS-RAD CAS DAR Module *Frameworks*.
Front-end design uses [Bootstrap](https://getbootstrap.com/).

## Features:

- Visitors can browse (read) the ads and search for them.
- Visitors can register as users (create) of the application.
- Users can log into the application.
- Users can do everything visitors can do, but also create new ads, update and delete the ads which they created.
- At time of creating or updating an ad, users must select a category, can optionally select multiple tags and can optionally link up to 3 photos by specifying their URL.
- Admins can do everything visitors can do, but also create new ads, update and delete every ad (regardless of their author).
- Admins can also create, read, update and delete categories and tags, which are available to all users for selection at time of creating and updating ads.
- Admins can read, update and delete all users.
- Users and admins can log out of the application.
- The search feature enforces that a category is selected. The search can be further refined by specifying a query string, to be matched against the ad title or description, and one or several tags.

## Getting started:

1. Update the database-related properties stored in `resources/application.properties` to those of your environment.
2. Run `classified_ads_schema.sql` as found in the `resources` folder in order to set up the database schema.
3. Run `classified_ads_data.sql` as found in the `resources` folder in order to seed the database with data.

This database comes with the following users:

| username | password | roles                |
|----------|----------|----------------------|
|admin     |admin     | ROLE_USER, ROLE_ADMIN|
|boss      |boss      | ROLE_USER, ROLE_ADMIN|
|greg      |greg      | ROLE_USER            |
|mike      |mike      | ROLE_USER            |
|bob       |bob       | ROLE_USER            |

The database schema is as follows:

![DB Schema](https://github.com/ldidioni/classified-ads/blob/main/src/main/resources/classified_ads_schema.png)

Note: the `rating` and `ratings_nb` fields of the `Users` table are unused at the moment. They are intended at supporting the rating of sellers by other users.