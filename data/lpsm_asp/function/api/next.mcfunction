# Made by: @Legopitstop
# Desc: Change to next pose.
#
# Called By: Public

# Change to next pose
data modify storage lpsm_asp:poses temp.id set from entity @n[type=marker, tag=ArmorStandData, limit=1] data.pose

execute store result score #len lpsm_asp.util run data get storage lpsm_asp:poses poses
execute if score #len lpsm_asp.util matches 1.. run function lpsm_asp:armor_stand/next_loop

title @a[tag=this] actionbar {"type": "translatable", "translate": "Pose: %s", "with": [{"type": "nbt", "storage": "lpsm_asp:poses", "interpret": true, "nbt": "pose.display_name"}]}
playsound minecraft:ui.button.click neutral @a[tag=this] ~ ~ ~ 0.3

# Apply pose
function lpsm_asp:api/set_pose
