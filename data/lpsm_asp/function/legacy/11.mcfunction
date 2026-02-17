# Made by: @Legopitstop
# Desc: can can a pose
#
# Called By: lpsm_asp:redstone && lpsm_asp:comparator

# reset
function lpsm_asp:armor_stand/reset

data modify entity @s Pose set from storage lpsm_asp:poses poses[{id: "can_can_a"}].Pose
tag @s add pose_11
