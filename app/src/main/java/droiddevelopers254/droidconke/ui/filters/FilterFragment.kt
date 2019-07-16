package droiddevelopers254.droidconke.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.Level
import droiddevelopers254.droidconke.models.Stage
import droiddevelopers254.droidconke.models.Type
import droiddevelopers254.droidconke.ui.widget.RoundedBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : RoundedBottomSheetFragment() {
    private val filterStore = FilterStore.instance

    private var onFilterChanged: (Filter) -> Unit = {}

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        closeButton.setOnClickListener { dismiss() }

        favoritesChip.isChecked = filterStore.filter.isFavorites
        favoritesChip.isCloseIconVisible = favoritesChip.isChecked

        favoritesChip.setOnCheckedChangeListener { compoundButton, isChecked ->
            val chip = compoundButton as Chip
            chip.isCloseIconVisible = isChecked
            filterStore.toggleFavorites()
            onFilterChanged(filterStore.filter)
        }
        favoritesChip.setOnCloseIconClickListener {
            val chip = it as Chip
            chip.isChecked = false
        }

        val stages = Stage.values().toList().minus(Stage.None).map { it.name }
        val stageChips = getChips(stages)
        stageChips.forEachIndexed { index, chip ->
            chip.isChecked = filterStore.filter.stages.contains(Stage.values()[index])
            chip.isCloseIconVisible = chip.isChecked

            chip.setOnCheckedChangeListener { compoundButton, isChecked ->
                val c = compoundButton as Chip
                c.isCloseIconVisible = isChecked
                filterStore.toggleStage(Stage.values()[index])
                onFilterChanged(filterStore.filter)
            }
            chip.setOnCloseIconClickListener {
                val c = it as Chip
                c.isChecked = false
            }
            stagesChipGroup.addView(chip)
        }

        val types = Type.values().toList().minus(Type.None).map { it.value }
        val typeChips = getChips(types)
        typeChips.forEachIndexed { index, chip ->
            chip.isChecked = filterStore.filter.types.contains(Type.values()[index])
            chip.isCloseIconVisible = chip.isChecked

            chip.setOnCheckedChangeListener { compoundButton, isChecked ->
                val c = compoundButton as Chip
                c.isCloseIconVisible = isChecked
                filterStore.toggleType(Type.values()[index])
                onFilterChanged(filterStore.filter)
            }
            chip.setOnCloseIconClickListener {
                val c = it as Chip
                c.isChecked = false
            }
            typesChipGroup.addView(chip)
        }

        val levels = Level.values().toList().minus(Level.None).map { it.name }
        val levelChips = getChips(levels)
        levelChips.forEachIndexed { index, chip ->
            chip.isChecked = filterStore.filter.levels.contains(Level.values()[index])
            chip.isCloseIconVisible = chip.isChecked

            chip.setOnCheckedChangeListener { compoundButton, isChecked ->
                val c = compoundButton as Chip
                c.isCloseIconVisible = isChecked
                filterStore.toggleLevel(Level.values()[index])
                onFilterChanged(filterStore.filter)
            }
            chip.setOnCloseIconClickListener {
                val c = it as Chip
                c.isChecked = false
            }
            levelChipGroup.addView(chip)
        }
    }

    private fun getChips(values: List<String>): List<Chip> {
        return values.map { value ->
            FilterChip(requireContext()).apply { text = value }
        }
    }

    companion object {
        fun newInstance(
                onFilterChanged: (Filter) -> Unit
        ) = FilterFragment().apply {
            this.onFilterChanged = onFilterChanged
        }
    }
}