# Made by: @Legopitstop
# Desc: Toggle the pose of the armor stand
#
# Called By: Advancement lpsm_asp:change_pose

tag @s add this
execute as @e[type=interaction, tag=ArmorStandPose] run function lpsm_asp:armor_stand/find_targets
execute as @e[type=armor_stand, tag=this] at @s run function lpsm_asp:api/next

# reset
tag @s remove this
tag @e[type=armor_stand, tag=this] remove this
tag @e[type=marker, tag=this] remove this
tag @e[type=interaction, tag=this] remove this
advancement revoke @a only lpsm_asp:change_pose
