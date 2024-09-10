package com.walkS.yiprogress.ui.widget.steps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.utils.clickableWithoutRipple

@Composable
fun WeSteps(
    options: List<(@Composable () -> Unit)?>,
    value: Int = 0,
    isVertical: Boolean = true,
    onItemClick: (index: Int) -> Unit = {}
) {
    if (isVertical) {
        Column {
            StepList(options, value, true, onItemClick = { p ->
                onItemClick.invoke(p)
            })
        }
    } else {
        Row {
            StepList(options, value, false, onItemClick = { p ->
                onItemClick.invoke(p)
            })
        }
    }
}

@Composable
fun HorizontalTextLabelStep(
    options: List<String>,
    value: Int = 0,
    onItemClick: (index: Int) -> Unit = {}
) {
    val composeList= mutableListOf<@Composable () -> Unit>()
    options.forEachIndexed { index, s ->
        composeList.add { StepTextLabel(s, value == index) }
    }
    Row {
        StepList(composeList,
            value, false, onItemClick = { p ->
                onItemClick.invoke(p)
            })
    }
}

@Composable
fun StepTextLabel(text: String, isArrived: Boolean) {
    Text(
        text,
        fontWeight = if (isArrived) FontWeight.Bold else FontWeight.Normal,
        color = if (isArrived) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    )
}

@Preview()
@Composable
fun MyStepPreview() {
    val selectedIndex = mutableStateOf(0)
    WeSteps(
        options = listOf({ "待面试" }, { "已面试" }, { "已通过" }),
        value = selectedIndex.value,
        isVertical = false,
        {
            selectedIndex.value = it
        }
    )
}

@Composable
fun StepList(
    items: List<(@Composable () -> Unit)?>,
    value: Int,
    isVertical: Boolean,
    onItemClick: (index: Int) -> Unit = {}
) {
    items.forEachIndexed { index, content ->
        Box(modifier = Modifier.clickableWithoutRipple {
            onItemClick.invoke(index)
        }) {
            StepItem(
                isActive = index <= value,
                isFirst = index == 0,
                isLast = index == items.lastIndex,
                isLastActive = index == value,
                isVertical,
                content
            )
        }
    }
}

@Composable
private fun StepItem(
    isActive: Boolean,
    isFirst: Boolean,
    isLast: Boolean,
    isLastActive: Boolean,
    isVertical: Boolean,
    content: (@Composable () -> Unit)?
) {
    val activeColor = MaterialTheme.colorScheme.primary
    val defaultColor = MaterialTheme.colorScheme.outline

    Box(
        contentAlignment = if (isVertical) Alignment.TopStart else Alignment.TopCenter,
        modifier = Modifier
            .drawBehind {
                val color = if (isActive) activeColor else defaultColor
                val offset = 12.dp.toPx()
                // 绘制小圆点
                drawCircle(
                    color = color,
                    radius = 6.dp.toPx(),
                    center = Offset(if (isVertical) offset else size.width / 2, offset)
                )

                // 绘制连接线
                if (isVertical) {
                    if (!isLast) {
                        drawLine(
                            color = if (isLastActive) defaultColor else color,
                            start = Offset(offset, offset * 2),
                            end = Offset(offset, size.height),
                            strokeWidth = 8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                } else {
                    if (!isFirst) {
                        drawLine(
                            color = color,
                            start = Offset(0f, offset),
                            end = Offset(size.width / 2 - offset, offset),
                            strokeWidth = 8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                    if (!isLast) {
                        drawLine(
                            color = if (isLastActive) defaultColor else color,
                            start = Offset(size.width / 2 + offset, offset),
                            end = Offset(size.width, offset),
                            strokeWidth = 8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
            .padding(if (isVertical) PaddingValues(start = 36.dp) else PaddingValues(top = 36.dp))
            .sizeIn(
                if (isVertical) 0.dp else 80.dp,
                if (!isVertical) 0.dp else 80.dp
            )
    ) {
        content?.invoke()
    }
}
