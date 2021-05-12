package co.zsmb.cleanchat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import co.zsmb.cleanchat.R
import co.zsmb.cleanchat.data.User
import co.zsmb.cleanchat.data.randomUserList
import co.zsmb.cleanchat.ui.theme.AppColors
import kotlin.math.pow
import kotlin.math.roundToInt

private val HEADER_SIZE = 360.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Contacts(navController: NavController) {
    val swipeableState = rememberSwipeableState(1f)
    val headerSizePx = with(LocalDensity.current) { HEADER_SIZE.toPx() }
    val anchors = mapOf(0f to 0f, headerSizePx to 1f)

    val onNewDelta: (Float) -> Float = { delta ->
        swipeableState.performDrag(delta)
    }
    val nestedScrollDispatcher = remember { NestedScrollDispatcher() }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y > 0) {
                    return Offset.Zero
                }
                val vertical = available.y
                val weConsumed = onNewDelta(vertical)
                return Offset(x = 0f, y = weConsumed)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y < 0) {
                    return Offset.Zero
                }
                val vertical = available.y
                val weConsumed = onNewDelta(vertical)
                return Offset(x = 0f, y = weConsumed)
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                swipeableState.performFling(available.y)
                return available
            }
        }
    }

    val progress: Float = (swipeableState.offset.value) / headerSizePx

    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection, nestedScrollDispatcher)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.4f) },
                orientation = Orientation.Vertical,
            )
            .background(AppColors.bgDark)
    ) {
        Column(Modifier.alpha(progress)) {
            Toolbar(onButtonClicked = { navController.navigate("messages") })
            Searchbar()
            Favorites()
        }

        val currentOffset: Dp = with(LocalDensity.current) {
            swipeableState.offset.value.roundToInt().coerceAtLeast(0).toDp()
        }

        ContactList(currentOffset)
    }
}

@Composable
private fun ContactList(topOffset: Dp) {
    val users = remember { randomUserList(size = 20) }

    val state: LazyListState = rememberLazyListState()

    val cornerRatio = (topOffset / (HEADER_SIZE / 2) - 1f).coerceAtLeast(0f)
    val currentCorner = when {
        cornerRatio <= 1f -> 36.dp * cornerRatio
        else -> 36.dp * cornerRatio.pow(6)
    }

    LazyColumn(
        state = state,
        modifier = Modifier
            .offset(y = topOffset)
            .clip(RoundedCornerShape(currentCorner))
            .background(AppColors.bgLight),
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(users) { user ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 0.5.dp
            ) {
                UserRow(user)
            }
        }
    }
}

@Composable
private fun Toolbar(onButtonClicked: () -> Unit) {
    Row(
        Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            stringResource(R.string.contacts_title),
            color = AppColors.textLight,
            fontSize = 28.sp,
            modifier = Modifier.weight(1f, fill = true),
            fontWeight = FontWeight.Medium
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .size(32.dp)
                .background(AppColors.bgLight)
                .clickable(onClick = onButtonClicked),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Default.Add, null)
        }
    }
}

@Composable
private fun Searchbar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(AppColors.bgMedium)
            .padding(12.dp)
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = null,
            tint = AppColors.textMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(stringResource(R.string.search_hint), color = AppColors.textMedium)
    }
}

@Composable
private fun Favorites() {
    Column(Modifier.padding(top = 16.dp, bottom = 16.dp)) {
        Text(
            stringResource(R.string.favorites_title),
            color = AppColors.textLight,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
        )
        val users = remember { randomUserList(size = 10) }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp)
        ) {
            items(users) { user ->
                FavoriteUser(user)
            }
        }
    }
}

@Composable
private fun FavoriteUser(user: User) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Avatar(
                url = user.avatarUrl,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            if (user.isOnline) {
                Box(
                    Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(AppColors.bgDark),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(AppColors.onlineIndicator)
                    )
                }
            }
        }
        Text(
            user.name,
            color = AppColors.textLight,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp
        )
    }
}
