# Android SQLite Demo
Sqlite Store Image with serach in Recyclerview

# Insert Image in sqlite database with fetch image 
```
  Sqlite image using BOLD data type
  1. Upload image in imageview
  2. Image convert bitmap to byte array then insert into sqlite database
  3. sample code


    DatabaseHandler db = new DatabaseHandler();
    byte[] photo = imageViewToByte(imageView);
    db.insertData(photo);


    public static byte[] imageViewToByte(ImageView image) {

          Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
          ByteArrayOutputStream stream = new ByteArrayOutputStream();
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
          byte[] byteArray = stream.toByteArray();
          return byteArray;
      }
  4. Retrive Image in SQLite 
    Code:
     Cursor c = db.rawQuery("select * from USER where id =" + id, null);
     while (c.moveToNext()) {            
            byte[] imageBytes = c.getBlob(c.getColumnIndex("image"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
           imageview.setImageBitmap(bitmap);
        }
```
# Recyclerview search item
```
    Search codeo
    1. Creae search menu item

      <menu xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:android="http://schemas.android.com/apk/res/android">

          <item
              android:id="@+id/app_bar_search"
              android:icon="@drawable/ic_search"
              android:title="@string/search"
              app:showAsAction="always"
              app:actionViewClass="androidx.appcompat.widget.SearchView"/>
      </menu>
    =========================================================================
    2. Create a Searchable Configuration in res/xml/searchable.xml

        <searchable xmlns:android="http://schemas.android.com/apk/res/android"
              android:label="@string/app_name"
              android:hint="@string/search_hint" />
    =========================================================          
    3. In declare manifest file      
          <activity android:name=".User_List"
              android:label="User List">
              <meta-data android:name="android.app.searchable"
                  android:resource="@xml/searchable" />
              <intent-filter>
                  <action android:name="android.intent.action.SEARCH" />
              </intent-filter>
          </activity>
      ==========================================================
    4. Create Option menu in activity 
        @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.search_item, menu);

          SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
          SearchView searchView =(SearchView) menu.findItem(R.id.app_bar_search).getActionView();
          searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
          searchView.setMaxWidth(Integer.MAX_VALUE);
          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {
                  if(usersList.contains(query)){
                      userAdapter.getFilter().filter(query);
                  }else{
                      Toast.makeText(User_List.this, "No Match found",Toast.LENGTH_LONG).show();
                  }
                  return false;
              }

              @Override
              public boolean onQueryTextChange(String newText) {
                  userAdapter.getFilter().filter(newText);
                  return false;
              }
          });
          return true;
      }


      @Override
      public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          int id = item.getItemId();
          if(id == R.id.app_bar_search){
              return true;
          }
          return super.onOptionsItemSelected(item);
      }
    ==========================================
    
    5. Adapter implement Filterable class override filter method
        Sample Code: 
        @Override
          public Filter getFilter() {
              return new Filter() {
                  @Override
                  protected FilterResults performFiltering(CharSequence charSequence) {
                      String charString = charSequence.toString();
                      if(charString.isEmpty()){
                          usersList = userList;
                      }else{
                          ArrayList<USER> filteredList = new ArrayList<>();
                          for(USER user: userList){
                              if (user.getName().toLowerCase().contains(charString.toLowerCase()) || user.getMobile().contains(charSequence)) {
                                  filteredList.add(user);
                              }
                          }
                          usersList = filteredList;
                      }
                      FilterResults filterResults = new FilterResults();
                      filterResults.values = usersList;
                      return filterResults;
                  }

                  @Override
                  protected void publishResults(CharSequence constraint, FilterResults results) {
                      usersList = (ArrayList<USER>)results.values;
                      notifyDataSetChanged();
                  }
              };
          }
 ```
      
