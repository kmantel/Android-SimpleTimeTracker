package com.example.util.simpletimetracker.domain.extension

import com.example.util.simpletimetracker.domain.model.DayOfWeek
import com.example.util.simpletimetracker.domain.model.Range
import com.example.util.simpletimetracker.domain.model.RecordType
import com.example.util.simpletimetracker.domain.model.RecordTypeCategory
import com.example.util.simpletimetracker.domain.model.RecordsFilter

fun List<RecordsFilter>.getTypeIds(): List<Long> {
    return filterIsInstance<RecordsFilter.Activity>()
        .map(RecordsFilter.Activity::typeIds)
        .flatten()
}

fun List<RecordsFilter>.getCategoryItems(): List<RecordsFilter.CategoryItem> {
    return filterIsInstance<RecordsFilter.Category>()
        .map(RecordsFilter.Category::items)
        .flatten()
}

fun List<RecordsFilter>.getCategoryIds(): List<Long> {
    return getCategoryItems()
        .filterIsInstance<RecordsFilter.CategoryItem.Categorized>()
        .map(RecordsFilter.CategoryItem.Categorized::categoryId)
}

fun List<RecordsFilter>.getTypeIdsFromCategories(
    recordTypes: List<RecordType>,
    recordTypeCategories: List<RecordTypeCategory>
): List<Long> {
    return getCategoryIds()
        .takeUnless { it.isEmpty() }
        ?.let { categoryIds ->
            recordTypeCategories
                .filter { it.categoryId in categoryIds }
                .map(RecordTypeCategory::recordTypeId)
        }
        .orEmpty()
        .let { selectedCategorizedTypes ->
            if (getCategoryItems().hasUncategorizedItem()) {
                val categorizedTypes = recordTypeCategories
                    .map(RecordTypeCategory::recordTypeId)
                    .distinct()

                selectedCategorizedTypes + recordTypes
                    .filter { it.id !in categorizedTypes }
                    .map(RecordType::id)
            } else {
                selectedCategorizedTypes
            }
        }
}

fun List<RecordsFilter>.getAllTypeIds(
    recordTypes: List<RecordType>,
    recordTypeCategories: List<RecordTypeCategory>
): List<Long> {
    return getTypeIds() + getTypeIdsFromCategories(recordTypes, recordTypeCategories)
}

fun List<RecordsFilter>.getCommentItems(): List<RecordsFilter.CommentItem> {
    return filterIsInstance<RecordsFilter.Comment>()
        .map(RecordsFilter.Comment::items)
        .flatten()
}

fun List<RecordsFilter>.getDate(): Range? {
    return filterIsInstance<RecordsFilter.Date>()
        .map(RecordsFilter.Date::range)
        .firstOrNull()
}

fun List<RecordsFilter>.getSelectedTags(): List<RecordsFilter.TagItem> {
    return filterIsInstance<RecordsFilter.SelectedTags>()
        .map(RecordsFilter.SelectedTags::items)
        .flatten()
}

fun List<RecordsFilter>.getFilteredTags(): List<RecordsFilter.TagItem> {
    return filterIsInstance<RecordsFilter.FilteredTags>()
        .map(RecordsFilter.FilteredTags::items)
        .flatten()
}

fun List<RecordsFilter>.getManuallyFilteredRecordIds(): List<Long> {
    return filterIsInstance<RecordsFilter.ManuallyFiltered>()
        .map(RecordsFilter.ManuallyFiltered::recordIds)
        .flatten()
}

fun List<RecordsFilter>.getDaysOfWeek(): List<DayOfWeek> {
    return filterIsInstance<RecordsFilter.DaysOfWeek>()
        .map(RecordsFilter.DaysOfWeek::items)
        .flatten()
}

fun List<RecordsFilter>.getTimeOfDay(): Range? {
    return filterIsInstance<RecordsFilter.TimeOfDay>()
        .map(RecordsFilter.TimeOfDay::range)
        .firstOrNull()
}

fun List<RecordsFilter>.getDuration(): Range? {
    return filterIsInstance<RecordsFilter.Duration>()
        .map(RecordsFilter.Duration::range)
        .firstOrNull()
}

fun List<RecordsFilter.TagItem>.getTaggedIds(): List<Long> {
    return filterIsInstance<RecordsFilter.TagItem.Tagged>()
        .map(RecordsFilter.TagItem.Tagged::tagId)
}

fun List<RecordsFilter>.hasUntrackedFilter(): Boolean {
    return any { it is RecordsFilter.Untracked }
}

fun List<RecordsFilter>.hasMultitaskFilter(): Boolean {
    return any { it is RecordsFilter.Multitask }
}

fun List<RecordsFilter>.hasActivityFilter(): Boolean {
    return any { it is RecordsFilter.Activity }
}

fun List<RecordsFilter>.hasCategoryFilter(): Boolean {
    return any { it is RecordsFilter.Category }
}

fun List<RecordsFilter.CommentItem>.hasNoComment(): Boolean {
    return any { it is RecordsFilter.CommentItem.NoComment }
}

fun List<RecordsFilter.CommentItem>.hasAnyComment(): Boolean {
    return any { it is RecordsFilter.CommentItem.AnyComment }
}

fun List<RecordsFilter.CommentItem>.getComments(): List<String> {
    return filterIsInstance<RecordsFilter.CommentItem.Comment>()
        .map(RecordsFilter.CommentItem.Comment::text)
}

fun List<RecordsFilter>.hasSelectedTagsFilter(): Boolean {
    return any { it is RecordsFilter.SelectedTags }
}

fun List<RecordsFilter.TagItem>.hasUntaggedItem(): Boolean {
    return any { it is RecordsFilter.TagItem.Untagged }
}

fun List<RecordsFilter.CategoryItem>.hasUncategorizedItem(): Boolean {
    return any { it is RecordsFilter.CategoryItem.Uncategorized }
}

fun List<RecordsFilter>.hasManuallyFiltered(): Boolean {
    return any { it is RecordsFilter.ManuallyFiltered }
}
