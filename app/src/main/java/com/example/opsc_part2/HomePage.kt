// Import statements
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import androidx.navigation.NavController
import android.graphics.BitmapFactory
import android.net.Uri

// Composable function for HomePage
@Composable
fun HomePage(navController: NavController) {
    // Mutable state variables to hold data
    var category by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(listOf<String>()) }
    var selectedCategory by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dateOfAcquisition by remember { mutableStateOf("") }
    var itemsInCategory by remember { mutableStateOf(mutableMapOf<String, CategoryInfo>()) }
    var showCategoryDetails by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Remembered scroll state
    val scrollState = rememberScrollState()
    // Context reference
    val context = LocalContext.current
    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Column layout for the HomePage
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title for categories
        Text(
            text = "Categories",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Text field to enter categories
        Text(
            text = "Enter your categories",
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Text field to input category
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text(text = "Category") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add category
        Button(
            onClick = {
                if (category.isNotBlank()) {
                    categories = categories + category
                    category = "" // Clear the input field after adding
                }
            }
        ) {
            Text(text = "Add Category")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title for selecting category
        Text(
            text = "Select a Category",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // LazyColumn to display categories
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
            items(categories.sorted()) { category ->
                Text(
                    text = category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedCategory = category
                        }
                        .padding(8.dp),
                    fontSize = 20.sp,
                    fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Add information to selected category
        if (selectedCategory.isNotEmpty()) {
            Text(
                text = "Add Info to $selectedCategory",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            // Text fields to input goal, description, and date of acquisition
            OutlinedTextField(
                value = goal,
                onValueChange = { goal = it },
                label = { Text(text = "Goal (Number of Items)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            OutlinedTextField(
                value = dateOfAcquisition,
                onValueChange = { dateOfAcquisition = it },
                label = { Text(text = "Date of Acquisition") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Button to launch image picker
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Upload Picture")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display selected image
            if (imageUri != null) {
                val bitmap = remember { BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri!!)) }
                Image(
                    painter = BitmapPainter(bitmap.asImageBitmap()),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to add item to category
            Button(
                onClick = {
                    if (selectedCategory.isNotEmpty() && goal.isNotEmpty() && imageUri != null) {
                        val newItem = Item(description, dateOfAcquisition, imageUri.toString())
                        val categoryInfo = itemsInCategory[selectedCategory] ?: CategoryInfo(goal, mutableListOf())
                        categoryInfo.items.add(newItem)
                        itemsInCategory[selectedCategory] = categoryInfo
                        // Clear the input fields after adding
                        goal = ""
                        description = ""
                        dateOfAcquisition = ""
                        imageUri = null
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Add Item")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    showCategoryDetails = !showCategoryDetails
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = if (showCategoryDetails) "Hide Category Details" else "View Category Details")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showCategoryDetails) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    categories.forEach { category ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Category: $category",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            val categoryInfo = itemsInCategory[category]
                            if (categoryInfo != null) {
                                categoryInfo.items.forEach { item ->
                                    Text(text = "Description: ${item.description}")
                                    Text(text = "Goal: ${categoryInfo.goal}")
                                    Text(text = "Date of Acquisition: ${item.dateOfAcquisition}")
                                    item.imageUrl?.let { url ->
                                        Image(
                                            painter = rememberImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(url)
                                                    .build()
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.size(100.dp)
                                        )
                                        Spacer(modifier = Modifier.height(15.dp))
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                                Spacer(modifier = Modifier.height(7.dp))
                            }

                        }
                    }
                }
            }
        }
    }
}

data class Item(val description: String, val dateOfAcquisition: String, val imageUrl: String)

data class CategoryInfo(var goal: String, val items: MutableList<Item>)
