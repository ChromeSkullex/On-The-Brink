// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class African_Penguin<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "african_penguin"), "main");
	private final ModelPart Body;
	private final ModelPart Tail;
	private final ModelPart Neck;
	private final ModelPart Neck2;
	private final ModelPart Head2;
	private final ModelPart Jaw;
	private final ModelPart RightLeg;
	private final ModelPart RightCalf;
	private final ModelPart RightFoot;
	private final ModelPart LeftLeg;
	private final ModelPart LeftCalf;
	private final ModelPart LeftFoot;
	private final ModelPart LeftArm;
	private final ModelPart LeftFin;
	private final ModelPart RightArm;
	private final ModelPart RightFin;

	public African_Penguin(ModelPart root) {
		this.Body = root.getChild("Body");
		this.Tail = this.Body.getChild("Tail");
		this.Neck = this.Body.getChild("Neck");
		this.Neck2 = this.Neck.getChild("Neck2");
		this.Head2 = this.Neck2.getChild("Head2");
		this.Jaw = this.Head2.getChild("Jaw");
		this.RightLeg = this.Body.getChild("RightLeg");
		this.RightCalf = this.RightLeg.getChild("RightCalf");
		this.RightFoot = this.RightCalf.getChild("RightFoot");
		this.LeftLeg = this.Body.getChild("LeftLeg");
		this.LeftCalf = this.LeftLeg.getChild("LeftCalf");
		this.LeftFoot = this.LeftCalf.getChild("LeftFoot");
		this.LeftArm = this.Body.getChild("LeftArm");
		this.LeftFin = this.LeftArm.getChild("LeftFin");
		this.RightArm = this.Body.getChild("RightArm");
		this.RightFin = this.RightArm.getChild("RightFin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 9.9F, 1.0F));

		PartDefinition Torso_r1 = Body.addOrReplaceChild("Torso_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -1.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, 5.9F, 4.5F));

		PartDefinition Tail_r1 = Tail.addOrReplaceChild("Tail_r1", CubeListBuilder.create().texOffs(48, 20).addBox(-2.0F, -0.5982F, -1.1296F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9163F, 0.0F, 0.0F));

		PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create(), PartPose.offset(0.0F, -3.7F, -0.6F));

		PartDefinition Neck_r1 = Neck.addOrReplaceChild("Neck_r1", CubeListBuilder.create().texOffs(24, 23).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -0.3F, -3.4F, 0.0436F, 0.0F, 0.0F));

		PartDefinition Neck2 = Neck.addOrReplaceChild("Neck2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.3F, -0.6F));

		PartDefinition Neck2_r1 = Neck2.addOrReplaceChild("Neck2_r1", CubeListBuilder.create().texOffs(34, 10).addBox(-3.0F, -4.0F, 1.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition Head2 = Neck2.addOrReplaceChild("Head2", CubeListBuilder.create(), PartPose.offset(0.0F, -2.7F, 0.6F));

		PartDefinition Beak_r1 = Head2.addOrReplaceChild("Beak_r1", CubeListBuilder.create().texOffs(42, 34).addBox(-1.0F, -2.0F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.8F, -7.1F, 0.0436F, 0.0F, 0.0F));

		PartDefinition Head_r1 = Head2.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(0, 23).addBox(-2.0F, -4.1309F, -2.9971F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.0F, -0.3F, -0.1F, 0.0436F, 0.0F, 0.0F));

		PartDefinition Jaw = Head2.addOrReplaceChild("Jaw", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition Jaw_r1 = Jaw.addOrReplaceChild("Jaw_r1", CubeListBuilder.create().texOffs(0, 46).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -4.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -2.1F, -2.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 7.1F, 1.5F));

		PartDefinition RightCalf = RightLeg.addOrReplaceChild("RightCalf", CubeListBuilder.create().texOffs(38, 49).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.9F, 0.0F));

		PartDefinition RightFoot = RightCalf.addOrReplaceChild("RightFoot", CubeListBuilder.create().texOffs(18, 45).addBox(-3.0F, 0.1F, -4.5F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 3.0F, 0.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(24, 34).addBox(-2.0F, -2.1F, -2.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 7.1F, 1.5F));

		PartDefinition LeftCalf = LeftLeg.addOrReplaceChild("LeftCalf", CubeListBuilder.create().texOffs(48, 49).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.9F, 0.0F));

		PartDefinition LeftFoot = LeftCalf.addOrReplaceChild("LeftFoot", CubeListBuilder.create().texOffs(42, 43).addBox(-1.0F, 0.1F, -4.5F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 3.0F, 0.0F));

		PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(16, 51).addBox(0.0F, -1.1F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.9F, -1.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition LeftFin = LeftArm.addOrReplaceChild("LeftFin", CubeListBuilder.create().texOffs(34, 0).addBox(-11.0F, -3.0F, 0.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 2.4F, 0.0F));

		PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(48, 29).addBox(-2.0F, -1.1F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.9F, -1.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition RightFin = RightArm.addOrReplaceChild("RightFin", CubeListBuilder.create().texOffs(34, 5).addBox(-11.0F, -2.5F, 0.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.9F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}