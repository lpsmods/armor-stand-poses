# Made by: @Legopitstop
# Desc: no pose
#
# Called By: lpsm_asp:redstone && lpsm_asp:comparator

# reset
function lpsm_asp:armor_stand/reset

data modify entity @s Pose set from storage lpsm_asp:poses poses[{id: "none"}].Pose
tag @s add pose_2
