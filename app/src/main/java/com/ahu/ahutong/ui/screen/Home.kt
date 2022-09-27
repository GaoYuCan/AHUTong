package com.ahu.ahutong.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ahu.ahutong.R
import com.ahu.ahutong.data.AHURepository
import com.ahu.ahutong.data.dao.AHUCache
import com.ahu.ahutong.data.model.Course
import com.ahu.ahutong.ui.page.state.DiscoveryViewModel
import com.ahu.ahutong.ui.page.state.ScheduleViewModel
import com.ahu.ahutong.ui.screen.component.AtAGlance
import com.ahu.ahutong.ui.screen.component.BathroomCard
import com.ahu.ahutong.ui.screen.component.CampusCard
import com.ahu.ahutong.ui.screen.component.CourseCard
import com.ahu.ahutong.ui.screen.component.EmptyCourseCard
import com.ahu.ahutong.ui.screen.component.FunctionalButton
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.kyant.monet.n1
import com.kyant.monet.withNight
import java.util.*

@Composable
fun Home(
    discoveryViewModel: DiscoveryViewModel = viewModel(),
    scheduleViewModel: ScheduleViewModel = viewModel(),
    navController: NavHostController
) {
    val user = AHUCache.getCurrentUser() ?: return
    val todayCourses = remember { mutableStateListOf<Course>() }
    val scheduleConfig by scheduleViewModel.scheduleConfig.observeAsState()
    val calendar = Calendar.getInstance(Locale.CHINA)
    val current = calendar.time.let {
        calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
    }
    LaunchedEffect(Unit) {
        AHURepository.getSchedule(scheduleViewModel.schoolYear, scheduleViewModel.schoolTerm, true)
            .onSuccess { courses ->
                todayCourses += courses
                    .filter { scheduleConfig?.week in it.startWeek..it.endWeek }
                    .filter { it.weekday == (scheduleConfig?.weekDay ?: 1) }
                    .sortedBy { it.startTime }
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(96.n1 withNight 10.n1)
            .padding(vertical = 24.dp)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AtAGlance(
            user = user
        )
        if (todayCourses.isNotEmpty()) {
            if (current <= scheduleViewModel.getCourseTimeRangeInMinutes(todayCourses.last()).last) {
                CourseCard(
                    scheduleViewModel = scheduleViewModel,
                    todayCourses = todayCourses,
                    current = current,
                    navController = navController
                )
            } else {
                EmptyCourseCard(navController = navController)
            }
        } else {
            EmptyCourseCard(navController = navController)
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            mainAxisAlignment = MainAxisAlignment.SpaceEvenly,
            crossAxisSpacing = 16.dp
        ) {
            FunctionalButton(
                stringId = R.string.title_schedule,
                iconId = R.mipmap.schedule_on,
                onClick = { navController.navigate("schedule") }
            )
            FunctionalButton(
                stringId = R.string.grade,
                iconId = R.mipmap.score,
                onClick = { navController.navigate("grade") }
            )
            FunctionalButton(
                stringId = R.string.phone_book,
                iconId = R.mipmap.telephone_directory,
                onClick = { navController.navigate("phone_book") }
            )
            FunctionalButton(
                stringId = R.string.exam,
                iconId = R.mipmap.examination_room,
                onClick = { navController.navigate("exam") }
            )
        }
        FlowRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 16.dp
        ) {
            CampusCard(
                balance = discoveryViewModel.balance,
                transitionBalance = discoveryViewModel.transitionBalance
            )
            BathroomCard(discoveryViewModel = discoveryViewModel)
        }
    }
}
