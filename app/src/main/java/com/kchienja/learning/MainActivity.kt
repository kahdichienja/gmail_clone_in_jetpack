package com.kchienja.learning

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kchienja.learning.model.MailModel
import com.kchienja.learning.ui.theme.*
import com.kchienja.learning.ui.theme.component.ExpandableCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Learning(viewModel = viewModel)
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun Learning(viewModel: MainActivityViewModel){
    val navColor = MaterialTheme.colors.primarySurface
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()


    val mainActivityViewModel: MainActivityViewModel = viewModel()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar =
        {
            CustomNaveBar(
                scope = scope,
                scaffoldState = scaffoldState,
                mainActivityViewModel = mainActivityViewModel
            )
        },
        drawerContent =
        {
            JetMailDrawer()
        },
        content =
        {
            JetMailBody(viewModel = viewModel)
            ModalBottomSheet(sheetState = sheetState)
            SearchDropdown(mainActivityViewModel = mainActivityViewModel)
            ProfileModalDropdown(mainActivityViewModel = mainActivityViewModel)

        },
        bottomBar = {BottomNav()},
        floatingActionButton =
        {
            ExtendedFloatingActionButton(
                text = { Text(text = "Compose", color = defaultRed)},
                icon = {
                    Icon(Icons.Outlined.Edit, contentDescription = "compose", tint = defaultRed)
                },
                onClick =
                {
                    scope.launch {
                        sheetState.show()
                    }
                },
                backgroundColor = navColor,
            )
        },
        floatingActionButtonPosition = FabPosition.End,

    ) 
}
@Composable
fun ProfileModalDropdown(mainActivityViewModel: MainActivityViewModel){

    val expand = mainActivityViewModel.dialogState.observeAsState(false)

    Column(modifier = Modifier.padding(10.dp)) {
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { !expand.value },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                IconButton(
                    onClick =
                    {
                        mainActivityViewModel.openDialog(status = false)
                    },
                ) {
                    Icon(Icons.Outlined.Close, contentDescription = "close", tint = GetTextColor())
                }
                GetJetMailText(
                    style = TextStyle(fontWeight = FontWeight.W900, fontSize = 20.sp, letterSpacing = 3.sp),
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
                )
                Text(text = ".")
            }
            Column {
                AccountsDropDown(showChip = true, username = "Ago Clinton", mail = "agoo@gmail.com", notificationCount = "2")
                Divider()
                AccountsDropDown( username = "Ago", mail = "agooclinton@gmail.com",)
                AccountsDropDown(username = "Clinton Agoo", mail = "agooesborn@gmail.com", notificationCount = "99+")
                Divider()

            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)){
                Text(text = "Privacy Policy", color = GetTextColor())
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = ".", color = GetTextColor(), style = TextStyle(fontWeight = FontWeight.W900))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Terms Of Service", color = GetTextColor())
            }

        }
    }

}

@Composable
fun SearchDropdown(mainActivityViewModel: MainActivityViewModel){
    val suggestions = listOf("Pending","Canceled","All")
    var selectedText by remember { mutableStateOf("") }

    val expand = mainActivityViewModel.inSearchMode.observeAsState(false)


    Column {

        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { !expand.value },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                IconButton(
                    onClick =
                    {
                        mainActivityViewModel.openSearch(status = false)
                    },
                ) {
                    Icon(Icons.Outlined.Close, contentDescription = "close", tint = GetTextColor())
                }
            }
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                }) {
                    Text(text = label, color = GetTextColor())
                }
            }
        }
    }

}
@ExperimentalMaterialApi
@Composable
fun BottomNav(){
    BottomNavigation {
        BottomNavigationItem(
            icon = {
                ItemComponent(imageVector = Icons.Filled.Email, isSelected = true, showIconBadge = true, count = "99+")
            },
            selected = true,
            onClick = { /*TODO*/ },
            label = {Text("Inbox", color = defaultRed)}
        )
        BottomNavigationItem(
            icon = {
                ItemComponent(imageVector = Icons.Outlined.DateRange)
            },
            selected = false,
            onClick = { /*TODO*/ },
            label = {Text("Calendar", color = grayBlack)}
        )
        BottomNavigationItem(
            icon = {
                ItemComponentPainter(painterResource = R.drawable.ic_meet)
            },
            selected = false,
            onClick = { /*TODO*/ },
            label = {Text("Meet", color = grayBlack)}
        )
    }
}
@ExperimentalMaterialApi
@Composable
fun ItemComponentPainter(showIconBadge: Boolean = false, isSelected: Boolean = false, count: String = "",painterResource: Int){
    if (showIconBadge)
        BadgeBox(badgeContent = {
            Text(text = count)
        }) {
            Icon(painterResource(painterResource), contentDescription = count, tint = if (isSelected) defaultRed else grayBlack)
        }
    else Icon(painterResource(painterResource), contentDescription = count, tint = if (isSelected) defaultRed else grayBlack)

}
@ExperimentalMaterialApi
@Composable
fun ItemComponent(showIconBadge: Boolean = false, isSelected: Boolean = false, count: String = "",imageVector: ImageVector){
    if (showIconBadge)
        BadgeBox(badgeContent = {
            Text(text = count)
        }) {
            Icon(imageVector, contentDescription = count, tint = if (isSelected) defaultRed else grayBlack)

        }
    else Icon(imageVector, contentDescription = count, tint = if (isSelected) defaultRed else grayBlack)

}
@ExperimentalMaterialApi
@Composable
fun ModalBottomSheet(sheetState: ModalBottomSheetState){
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CustomInputComponent()
        }
    ){}
}
@Composable
fun CustomInputComponent(){
    val subjectState = remember { mutableStateOf(TextFieldValue()) }
    val fromTextState = remember { mutableStateOf(TextFieldValue()) }
    val toTextState = remember { mutableStateOf(TextFieldValue()) }
    val composeTextState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(

            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(painterResource(id = R.drawable.ic_open_in_full), contentDescription = null, modifier = Modifier.clickable {  }, tint = grayBlack)
            Spacer(modifier = Modifier.width(10.dp))
            Icon(painterResource(id = R.drawable.ic_close_full), contentDescription = null, modifier = Modifier.clickable {  }, tint = grayBlack)
            Spacer(modifier = Modifier.width(10.dp))
            Icon(Icons.Filled.Close, contentDescription = null, modifier = Modifier.clickable {  }, tint = grayBlack)
        }
        OutlinedTextField(
            textStyle = TextStyle(color = grayBlack),
            leadingIcon = {
                Text("From:")
            },
            value = fromTextState.value,
            onValueChange = { fromTextState.value = it  },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(painterResource(id = R.drawable.ic_arrow_drop_down_24), contentDescription = "contentDescription", Modifier.clickable {  })
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            textStyle = TextStyle(color = grayBlack),
            leadingIcon = {
                Text("To:")
            },
            value = toTextState.value,
            onValueChange = { toTextState.value = it },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(painterResource(id = R.drawable.ic_arrow_drop_down_24), contentDescription = "contentDescription", Modifier.clickable {  })
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            textStyle = TextStyle(color = grayBlack),
            leadingIcon = {
                Text("Subject: ")
            },
            value = subjectState.value,
            onValueChange = { subjectState.value = it},
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            textStyle = TextStyle(color = grayBlack),
            leadingIcon = {
                Text(text = "Compose Mail")
            },
            value = composeTextState.value,
            onValueChange = { composeTextState.value = it},
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = defaultRed)},
                text = { Text(text = "Edit", color = defaultRed) },
                onClick = { /*TODO*/ },
                backgroundColor = MaterialTheme.colors.primarySurface,
            )
            Spacer(modifier = Modifier.width(25.dp))
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Send, contentDescription = "Add", tint = defaultRed)},
                text = { Text(text = "Send", color = defaultRed) },
                onClick = { /*TODO*/ },
                backgroundColor = MaterialTheme.colors.primarySurface
            )
        }

    }

}
@Composable
fun CustomNaveBar(scaffoldState: ScaffoldState, scope: CoroutineScope, mainActivityViewModel: MainActivityViewModel) {
    Row(modifier = Modifier.height(10.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
    }
    Card(
        Modifier
            .height(75.dp)
            .fillMaxWidth()
            .padding(top = 20.dp, end = 16.dp, start = 16.dp), elevation = 4.dp, shape = RoundedCornerShape(10.dp)) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()

                    }
                },
                modifier = Modifier.padding(start = 4.dp
                )
            ){
                Icon(
                    Icons.Filled.Menu,contentDescription = "menu",modifier = Modifier
                    .fillMaxHeight(),
                    tint = GetTextColor()
                )
            }
            IconButton(onClick = {
                mainActivityViewModel.openSearch(status = true)
            }) {
                Box(
                    modifier = Modifier
                        .padding(top = 9.dp)
                        .clickable { },
                ) {
                    Text(text = "Search.", color = GetTextColor())
                }
            }

            IconButton(onClick = {
                mainActivityViewModel.openDialog(status = true)
            }) {
                Image(
                    painterResource(id = R.drawable.profile),
                    contentDescription = " ",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(
                            2.dp, success, CircleShape,
                        )
                        .padding(4.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}
@Composable
fun JetMailDrawerContent(title: String, imageVector: Int,isSelected: Boolean = false, hasNotification: Boolean = false, notificationCount: Int = 0){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .offset(x = (-30).dp)
            .fillMaxWidth()
            .height(55.dp)
            .background(
                color = if (isSelected) selectedItemColor else if (isSystemInDarkTheme()) dDrawerPrimary else lPrimary,
                shape = CircleShape
            )
            .padding(end = 10.dp)

    ) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(18.dp)) {
            Row{
                Icon(
                   painterResource(imageVector),
                    contentDescription = "",
                    tint = if (isSelected) selectedItemColorText else GetTextColor(),
                    modifier = Modifier
                        .padding(start = 40.dp)
                        .height(30.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = title, color = if (isSelected) selectedItemColorText else GetTextColor())
            }
        }
        if (hasNotification)CustomChip(text = "new", count = notificationCount, selected = isSelected, modifier = Modifier.padding(10.dp))

    }

}
@Composable
private fun CustomChip(
    text: String,
    count: Int,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    // define properties to the chip
    // such as color, shape, width
    Surface(
        color = when {
            selected -> grayBlackPrimary
            else -> Color.Transparent
        },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> selectedItemColor
                else -> Color.LightGray
            }
        ),
        modifier = modifier
    ) {
        // Inside a Row pack the Image and text together to
        // show inside the chip
        Row(modifier = Modifier) {
            Text(
                text = count.toString(),
                color =  if (selected) selectedItemColorText else GetTextColor(),
                modifier = Modifier
                    .padding(start = 10.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    .size(18.dp)
                    .clip(CircleShape)
            )
            Text(
                text = text,
                color =  if (selected) selectedItemColorText else GetTextColor(),
                modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun JetMailBody(viewModel: MainActivityViewModel){
    JetMailMailScreen(
        mails = viewModel.mailItems,
        currentlyView = viewModel.currentMailItem,
//        onAddItem = todoViewModel::addItem,
//        onRemoveMail = viewModel::,
        onStartView = viewModel::onMailItemSelected,
        onViewItemChange = viewModel::onMailItemChange,
        onViewDone = viewModel::onMailDone,
    )

}
@Composable
fun  JetMailMailScreen(
    mails: List<MailModel>,
    currentlyView: MailModel?,
//    onAddMail: (MailModel) -> Unit,
//    onRemoveMail: (MailModel) -> Unit,
    onStartView: (MailModel) -> Unit,
    onViewItemChange: (MailModel) -> Unit,
    onViewDone: () -> Unit,
)
{
    Column {
        LazyColumn{
            items(mails.size){i ->

                if (currentlyView?.id == mails[i].id){
                    Column{
                        JetMailDetailedView(
                            mail = currentlyView,
                            onViewItemChange = onViewItemChange,
                            onViewDone = onViewDone,
//                            onRemoveMail = { onRemoveMail( mails[i]) }
                        )
                    }

                }else{
                    MessageListTile(
                        leading = mails[i].leading,
                        subject = mails[i].subject,
                        hasAttachment = mails[i].hasAttachment,
                        isImportant = mails[i].isImportant,
                        text = mails[i].text,
                        trailing = mails[i].trailing,
                        title = mails[i].title,
                        trailingText = mails[i].trailingText,
                        onStartView =  { onStartView(it) },
                        mail = mails[i]
                    )
                }
            }
        }
    }
}
@Composable
fun JetMailDetailedView(
    mail: MailModel,
    onViewItemChange: (MailModel) -> Unit,
    onViewDone: () -> Unit,
//    onRemoveMail: () -> Unit
){
    Column(modifier = Modifier
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(color = grayBlackPrimary)
        .fillMaxWidth()
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onViewDone, modifier = Modifier
                .widthIn(15.dp)
                .clickable { }) {
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = "Close Mail",
                    tint = GetTextColor(),
                )
            }
            Column{
                Row(modifier = Modifier.widthIn(15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        Icons.Outlined.Archive,
                        contentDescription = "Archive Mail",
                        tint = GetTextColor()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        Icons.Outlined.DeleteOutline,
                        contentDescription = "Delete Mail",
                        tint = GetTextColor()
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(Icons.Outlined.Mail, contentDescription = "Mail", tint = GetTextColor())
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        Icons.Outlined.MoreVert,
                        contentDescription = "More",
                        tint = GetTextColor()
                    )
                }
            }
        }

        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = mail.subject,
                style = TextStyle(
                    color = GetTextColor(),
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp
                )
            )
            Icon(Icons.Outlined.Star, contentDescription = "Star", tint = GetStarColor(isStarred = mail.isImportant))
        }
        Row {
            JetMailDetailListTile(mail = mail)
        }
    }
}
@Composable
fun JetMailDetailListTile(mail: MailModel)
{
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            CircleAvatar(id = R.drawable.profile)
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .widthIn(25.dp),) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(20.dp)) {
                    Column {
                        Row {
                            Text(text = mail.title, color = GetTextColor(), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700))
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = mail.trailingText, color = GetTextColor(), modifier = Modifier.padding(end = 20.dp))
                        }
                        Row{
                            Text(text = "To me", color = GetTextColor(), modifier = Modifier.padding(start = 5.dp))
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "expand", tint = GetTextColor())
                        }
                    }
                    Row{
                        Icon(Icons.Outlined.Reply, contentDescription = "reply", tint = GetTextColor())
                        Spacer(Modifier.width(10.dp))
                        Icon(Icons.Outlined.MoreVert, contentDescription = "more", tint = GetTextColor(), modifier = Modifier.padding(end = 5.dp))
                    }

                }
            }
        }

        Text(
            text = mail.text,
            color = GetTextColor(),
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.W300),
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 10.dp)
        )
        if (mail.hasAttachment)Spacer(modifier = Modifier.height(20.dp))
        if (mail.hasAttachment) Row( modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            mail.attachmentFiles?.forEach { file ->
                GetAttachmentIcon(ext = file.ext, fileName = file.fileName)
            }
        }
        if (mail.hasAttachment)Spacer(modifier = Modifier.height(20.dp))


    }

}

@Composable
fun JetMailDrawer(){
    Column {
        GetJetMailText(
            style = TextStyle(fontWeight = FontWeight.W900, fontSize = 30.sp, letterSpacing = 4.sp),
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
        )
        Divider(color = GetTextColor())
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_all_inbox, title = "All Boxes")
        Divider(color = GetTextColor())
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(
            isSelected = true, hasNotification = true,
            notificationCount =21, imageVector = R.drawable.ic_inbox,
            title = "Primary"
        )
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_outline_people, title = "Social")
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_label, title = "Promotions")
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_outline_info, title = "Updates")
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_outline_forum_24, title = "Forums")
        Spacer(modifier = Modifier.height(10.dp))
        Text("ALL LABELS", modifier = Modifier.padding(start = 20.dp), color = GetTextColor())
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_baseline_star_outline_24, title = "Starred")
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_baseline_label_important_24, title = "Important")
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_twotone_warning_24, title = "Spam")
        Spacer(modifier = Modifier.height(10.dp))
        JetMailDrawerContent(imageVector = R.drawable.ic_outline_delete_24, title = "Trash")
    }

}
@Composable
fun MessageListTile(
    mail: MailModel,
    leading: Int? = null,
    subject: String,
    hasAttachment: Boolean = false,
    isImportant: Boolean = false,
    text: String,
    trailing: ImageVector,
    title: String,
    trailingText: String,
    onStartView: (MailModel) -> Unit,
){
    val firstThreeAttachmentFile = mail.attachmentFiles?.take(2)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(17.dp)
            .clickable { onStartView(mail) },
//        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
        )
        {
            CircleAvatar(id = leading)
        }
        Column(horizontalAlignment = Alignment.Start, ){
            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()){
                Text(
                    text = title,
                    style = TextStyle(
                        color = GetTextColor(),
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp
                    )
                )
                Text(   text = trailingText, color = GetTextColor())
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = subject,
                    style = TextStyle(
                        color = GetTextColor()
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(trailing, contentDescription = "", tint = GetStarColor(isStarred = isImportant))
            }
            Row {
                Text(
                    text,
                    style = TextStyle(
                        color = grayBlackPrimary,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            if (hasAttachment) Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 5.dp)
            ){
                Spacer(modifier = Modifier.height(10.dp))
                firstThreeAttachmentFile?.forEach { file ->
                    Spacer(modifier = Modifier.width(5.dp))
                    GetAttachmentIcon(ext = file.ext, fileName = file.fileName)
                }
                if(mail.attachmentFiles?.size!! > 2) Text(text = "+ "+(mail.attachmentFiles.size - 2).toString()+" more", color = GetTextColor())
            }
        }

    }
}
@Composable
fun JetMailAttachmentChip(
    attachment: Int,
    text: String,
    selected: Boolean = true,
    modifier: Modifier = Modifier
){
    Surface(
        contentColor = when {
            selected -> GetAttachmentColor()
            else -> GetAttachmentColor()
        },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> GetAttachmentColor()
                else -> GetAttachmentColor()
            }
        ),
        modifier = modifier
    ) {
        // Inside a Row pack the Image and text together to
        // show inside the chip
        Row {
            Image(
                painterResource(id = attachment),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(color = GetTextColor())
            )
            Text(
                text = text,
                style = TextStyle(color = GetTextColor()),
                modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
            )
        }
    }
}
@Composable
fun CircleAvatar(id: Int? =null){
    if (id != null) {
        Image(
            painterResource(id),
            contentDescription = " ",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .height(40.dp)
                .clickable { }
                .width(40.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .border(
                    2.dp, success, CircleShape,
                )
                .padding(4.dp)
                .fillMaxHeight()
        )
    }else {
        Box(           // crop the image if it's not a square
        modifier = Modifier
            .height(40.dp)
            .clickable { }
            .width(40.dp)
            .clip(CircleShape)                       // clip to the circle shape
            .border(
                2.dp, success, CircleShape,
            )
            .padding(4.dp)
            .fillMaxHeight()
            .background(color = grayBlack)
        )
    }
}

@Composable
fun AccountsDropDown(showChip: Boolean = false, username: String, mail: String, notificationCount: String = ""){

     Row(
         Modifier
             .fillMaxWidth()
             .padding(start = 10.dp, top = 10.dp)) {
         CircleAvatar(id = R.drawable.profile)
         Column(Modifier.padding(start = 10.dp)) {
             Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                 Text(text = username, color = GetTextColor(), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700))
                 Text(text = notificationCount, color = GetTextColor(), modifier = Modifier.padding(end = 20.dp))
             }
             Text(text = mail, color = GetTextColor(), style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.W300))
             if (showChip)Spacer(modifier = Modifier.height(20.dp))
             if (showChip) JetMailAttachmentChip(attachment = R.drawable.ic_account, text = "Manage your Jet mail Account", selected = true)
             Spacer(modifier = Modifier.height(20.dp))
         }
     }

}
@Composable
fun GetJetMailText(modifier: Modifier = Modifier, style: TextStyle = TextStyle(),){
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                withStyle(style = SpanStyle(color = jetmail1)) {
                    append("J")
                }
                withStyle(style = SpanStyle(color = jetmail2)) {
                    append("e")
                }
                withStyle(style = SpanStyle(color = jetmail3)) {
                    append("t")
                }
                withStyle(style = SpanStyle(color = jetmail4)) {
                    append("M")
                }
                withStyle(style = SpanStyle(color = jetmail5)) {
                    append("a")
                }
                withStyle(style = SpanStyle(color = jetmail6)) {
                    append("i")
                }
                withStyle(style = SpanStyle(color = jetmail7)) {
                    append("l")
                }
            }
        },
        style = style,
        modifier= modifier
    )
}
@ExperimentalMaterialApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DefaultPreview() {

    LearningTheme {

//        Column {
//            AccountsDropDown(showChip = true, username = "Ago Clinton", mail = "agoo@gmail.com", notificationCount = "2")
//            Divider()
//            AccountsDropDown( username = "Ago Clinton", mail = "agoo@gmail.com",)
//            AccountsDropDown(username = "Ago Clinton", mail = "agoo@gmail.com", notificationCount = "99+")
//
//        }
//        Learning()
//        CustomInputComponent()
//    JetAlertDialog(isDialogOpen  =true)

//    JetMailDrawer()

//    Column {
//        LazyColumn{
//            items(mailList.size){index ->
//                MessageListTile(
//                    leading = mailList[index].leading,
//                    subject = mailList[index].subject,
//                    hasAttachment = mailList[index].hasAttachment,
//                    isImportant = mailList[index].isImportant,
//                    text = mailList[index].text,
//                    trailing = mailList[index].trailing,
//                    title = mailList[index].title,
//                    trailingText = mailList[index].trailingText,
//                    fileName = mailList[index].fileName,
//
//                    )
//            }
//        }
//    }

//        CustomNaveBar(
//            scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed)),
//            scope = rememberCoroutineScope()
//        )
    }
}

var mailList: List<MailModel> =  mutableListOf<MailModel>(
    MailModel(
        id = 1,
        title = "Linked In Job Alerts",
        subject = "3, jobs for 'mobile engineer",
        text = "View job in Chicago, Illinois, United StatesView job in Chicago,Illinois, ",
        trailingText = "Sep 6",
        trailing = Icons.Outlined.Star,
        leading = R.drawable.profile,
        isImportant = true,
        hasAttachment = false,
    ),
    MailModel(
        id = 2,
        title = "Linked In Job Alerts",
        subject = "3, jobs for 'mobile engineer",
        text = "View job in Chicago, Illinois, United StatesView job in Chicago, United ",
        trailingText = "Sep 6",
        trailing = Icons.Outlined.Star,
        leading = R.drawable.profile,
        isImportant = false,
        hasAttachment = true,
        ),
    MailModel(
        id= 3,
        title = "Course Hero",
        subject = "You file Uploads",
        text = "View job in Chicago, Illinois, United StatesView job in Chicago Illinois,",
        trailingText = "Sep 6",
        trailing = Icons.Outlined.Star,
        leading = R.drawable.profile,
        isImportant = true,
        hasAttachment = true,
    ),
)
