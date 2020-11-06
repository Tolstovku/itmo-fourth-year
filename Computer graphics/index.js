const loader = new THREE.TextureLoader();
var particleSystem;
var particles = [],
frame = 0;
var particleImage = new Image(); 
particleImage.src = 'textures/snow.png'; 

window.addEventListener('load', init, false);

function init() {
  createScene();

  createLights();

  createGround();
  createPole();
  createStop();
  createSnow();
  createCar();

  loop();
}

function createSnow() {

function randomRange(min, max) {
    return ((Math.random() * (max - min)) + min);
}
  PARTICLE_COUNT = 30000;
  var snowflakeTexture = loader.load("textures/snow.png")

    geometry = new THREE.Geometry();

  var assembleGeometry = function () {
    for (var i = 0; i < PARTICLE_COUNT; i++) {
      var particle = {
        position: new THREE.Vector3(randomRange(-1000, 1000), randomRange(0, 1000), randomRange(-1000, 1000)),
        velocity: new THREE.Vector3(0, -Math.random() * 5, 0)
      };
      geometry.vertices.push(particle.position);
    }
  };

  assembleGeometry();

  var particleMaterial = new THREE.PointsMaterial({
    color: '#FFF',
    map: snowflakeTexture,
    size: 2,
    blending: THREE.AdditiveBlending,
    transparent: true
  });
  particleSystem = new THREE.Points(geometry, particleMaterial);

  particleSystem.sortParticles = true;
  scene.add(particleSystem);

}

function loop() {
  renderer.render(scene, camera);
  // controls.update();
  if (carMesh != undefined){
    carMesh.position.x -=1;
    if (carMesh.position.x < -450) {
      carMesh.position.x = 450;
    }
  }


  // -- SNOW movement
  particleSystem.rotation.y = frame / 350
  particleSystem.position.y -=0.5;
  if (particleSystem.position.y < -600) {
    particleSystem.position.y = 100;
  }
  frame++;

  requestAnimationFrame(loop);
}


function createScene() {
  // Получаем ширину и высоту сцены и используем
  // их для установки размера и пропорций камеры,
  // а также для размера финального рендера
  HEIGHT = window.innerHeight;
  WIDTH = window.innerWidth;

  scene = new THREE.Scene();

  // Создание камеры
  aspectRatio = WIDTH / HEIGHT;
  fieldOfView = 45;
  nearPlane = 1;
  farPlane = 10000;
  camera = new THREE.PerspectiveCamera(
    fieldOfView,
    aspectRatio,
    nearPlane,
    farPlane
  );

  // Задаем позицию камеры в 3D пространстве
  camera.position.x = 0;
  camera.position.z = 50;
  camera.position.y = 20;

  // Создаем рендер
  renderer = new THREE.WebGLRenderer({
    alpha: true,
    antialias: true
  });

  controls = new THREE.PointerLockControls( camera, document.body );
  // controls = new THREE.OrbitControls(camera, document.body);
  document.body.addEventListener('click', function () {

    controls.lock();
  }
    , false);

  const onKeyDown = function (event) {
    switch (event.keyCode) {
      case 87: // w
        controls.moveForward(3)
        break;
      case 65: // a
        controls.moveRight(-3)
        break;
      case 83: // s
        controls.moveForward(-3)
        break;
      case 68: // d
        controls.moveRight(3)
        break;
    }
  };

  document.addEventListener('keydown', onKeyDown, false);

  // Задаем размер рендера (в нашем случае
  // он равен размеру сцены)
  renderer.setSize(WIDTH, HEIGHT);

  renderer.shadowMap.enabled = true;

  container = document.getElementById('world');
  container.appendChild(renderer.domElement);

  window.addEventListener('resize', handleWindowResize, false);
}

function handleWindowResize() {
  // обновим высоту, ширину камеры и рендера
  HEIGHT = window.innerHeight;
  WIDTH = window.innerWidth;
  renderer.setSize(WIDTH, HEIGHT);
  camera.aspect = WIDTH / HEIGHT;
  camera.updateProjectionMatrix();
}

var hemisphereLight, shadowLight;

function createLights() {
  // Свет лампочек
  bulbLightArray = []
  bulbLight = new THREE.PointLight(0xfffb00, 1, 1);
  bulbLight.position.set(0, 55, 10);
  bulbLightArray.push(bulbLight);

  bulbLight = new THREE.PointLight(0xfffb00, 1, 1);
  bulbLight.position.set(0, 55, 240);
  bulbLightArray.push(bulbLight);

  bulbLight = new THREE.PointLight(0xfffb00, 1, 1);
  bulbLight.position.set(180, 55, 10);
  bulbLightArray.push(bulbLight);

  bulbLight = new THREE.PointLight(0xfffb00, 1, 1);
  bulbLight.position.set(-180, 55, 10);
  bulbLightArray.push(bulbLight);

  bulbLight = new THREE.PointLight(0xfffb00, 1, 1);
  bulbLight.position.set(-180, 55, 240);
  bulbLightArray.push(bulbLight);

  bulbLight = new THREE.PointLight(0xfffb00, 1, 1);
  bulbLight.position.set(180, 55, 240);
  bulbLightArray.push(bulbLight);

  bulbLightArray.forEach(function (light) {
    light.distance = 200;
    // Разрешаем отбрасывание теней
    light.castShadow = true;
    // Определяем видимую область теней
    light.shadow.camera.left = -400;
    light.shadow.camera.right = 400;
    light.shadow.camera.top = 400;
    light.shadow.camera.bottom = -400;
    light.shadow.camera.near = 1;
    light.shadow.camera.far = 1000;

    light.shadow.mapSize.width = 2048;
    light.shadow.mapSize.height = 2048;
    scene.add(light);
  })

  //Свет звезд
  shadowLight = new THREE.DirectionalLight(0xffffff, .1);

  shadowLight.position.set(150, 350, 350);

  shadowLight.castShadow = true;

  shadowLight.shadow.camera.left = -400;
  shadowLight.shadow.camera.right = 400;
  shadowLight.shadow.camera.top = 400;
  shadowLight.shadow.camera.bottom = -400;
  shadowLight.shadow.camera.near = 1;
  shadowLight.shadow.camera.far = 1000;

  shadowLight.shadow.mapSize.width = 2048;
  shadowLight.shadow.mapSize.height = 2048;

  scene.add(shadowLight);
}

var Ground = function () {

  var asphaltTexture = loader.load('textures/asphalt.jpg', function (texture) {

    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
    texture.offset.set(0, 0);
    texture.repeat.set(2, 2);

  });
  this.mesh = new THREE.Object3D();
  let geometry = new THREE.BoxGeometry(1000, 1, 1000);
  let material = new THREE.MeshPhongMaterial({ color: 0x9c9c9c, map: asphaltTexture });
  let cube = new THREE.Mesh(geometry, material);
  cube.position.z = -450;
  this.mesh.add(cube);

  geometry = new THREE.BoxGeometry(1000, 1, 1000);
  cube = new THREE.Mesh(geometry, material);
  cube.position.z = 700;
  this.mesh.add(cube);

  //Road
  const roadTexture = loader.load('textures/road.png');
  geometry = new THREE.BoxGeometry(1000, 1, 150);
  material = new THREE.MeshPhongMaterial({ color: 0x9c9c9c, map: roadTexture });
  cube = new THREE.Mesh(geometry, material);
  cube.position.z = 130;
  cube.position.y = -5;
  this.mesh.add(cube);

  //Porebrik
  const texture = loader.load("textures/border.jpg");

  geometry = new THREE.BoxGeometry(1000, 15, 10);
  material = new THREE.MeshBasicMaterial({
    map: texture
  });

  texture.wrapS = THREE.RepeatWrapping;
  texture.wrapT = THREE.RepeatWrapping;


  cube = new THREE.Mesh(geometry, material);
  cube.geometry.computeBoundingBox();
  var max = cube.geometry.boundingBox.max;
  var min = cube.geometry.boundingBox.min;
  var height = max.y - min.y;
  var width = max.x - min.x;

  texture.repeat.set(width / 100, height / 100);

  texture.needsUpdate = true;



  cube.position.z = 200;
  cube.position.y = -5;
  this.mesh.add(cube);

  //Porebrik
  geometry = new THREE.BoxGeometry(1000, 15, 10);
  cube = new THREE.Mesh(geometry, material);
  cube.position.z = 50;
  cube.position.y = -5;
  this.mesh.add(cube);

};

var ground;

function createGround() {
  ground = new Ground();
  ground.mesh.position.set(0, 0, 0)
  scene.add(ground.mesh);
}

var Pole = function () {
  const poleTexture = loader.load("textures/stolb.jpg");
  this.mesh = new THREE.Object3D();
  const base = new THREE.CylinderGeometry(2, 2, 60);
  const base_material = new THREE.MeshLambertMaterial({ color: 0x7d7d7d, map: poleTexture });
  const base_mesh = new THREE.Mesh(base, base_material);
  base_mesh.castShadow = true;
  base_mesh.receiveShadow = true;
  this.mesh.add(base_mesh);

  const cap = new THREE.BoxGeometry(6, 3, 14);
  const cap_mesh = new THREE.Mesh(cap, base_material);
  cap_mesh.position.set(0, 30, 6);
  cap_mesh.castShadow = true;
  cap_mesh.receiveShadow = true;
  this.mesh.add(cap_mesh);

  const lamp = new THREE.SphereGeometry(2, 32, 32);
  const lamp_material = new THREE.MeshPhongMaterial({ color: 0xFFFFFF });
  const lamp_mesh = new THREE.Mesh(lamp, lamp_material);
  lamp_mesh.position.set(0, 28, 10);
  this.mesh.add(lamp_mesh);
}

function createPole() {
  pole = new Pole();
  pole.mesh.position.set(0, 30, 0);
  scene.add(pole.mesh);

  pole2 = new Pole()
  pole2.mesh.position.set(180, 30, 0);
  scene.add(pole2.mesh);

  pole3 = new Pole()
  pole3.mesh.position.set(-180, 30, 0);
  scene.add(pole3.mesh);

  pole4 = new Pole();
  pole4.mesh.position.set(0, 30, 250);
  pole4.mesh.rotateY(Math.PI);
  scene.add(pole4.mesh);

  pole5 = new Pole()
  pole5.mesh.position.set(180, 30, 250);
  pole5.mesh.rotateY(Math.PI);
  scene.add(pole5.mesh);

  pole6 = new Pole()
  pole6.mesh.position.set(-180, 30, 250);
  pole6.mesh.rotateY(Math.PI);
  scene.add(pole6.mesh);
}

var Stop = function () {
  //Опоры
  const pipeTexture = loader.load("textures/rust.jpg");
  this.mesh = new THREE.Object3D();
  let pipe = new THREE.BoxGeometry(4, 30, 4);
  let material = new THREE.MeshLambertMaterial({ color: 0x7d7d7d, map: pipeTexture });
  let pipe_mesh = new THREE.Mesh(pipe, material);
  pipe_mesh.castShadow = true;
  pipe_mesh.receiveShadow = true;
  this.mesh.add(pipe_mesh);

  pipe = new THREE.BoxGeometry(4, 30, 4);
  pipe_mesh = new THREE.Mesh(pipe, material);
  pipe_mesh.castShadow = true;
  pipe_mesh.receiveShadow = true;
  pipe_mesh.position.x = 40;
  this.mesh.add(pipe_mesh);

  pipe = new THREE.BoxGeometry(4, 30, 4);
  pipe_mesh = new THREE.Mesh(pipe, material);
  pipe_mesh.castShadow = true;
  pipe_mesh.receiveShadow = true;
  pipe_mesh.position.x = 40;
  pipe_mesh.position.z = 20;
  this.mesh.add(pipe_mesh);

  pipe = new THREE.BoxGeometry(4, 30, 4);
  pipe_mesh = new THREE.Mesh(pipe, material);
  pipe_mesh.castShadow = true;
  pipe_mesh.receiveShadow = true;
  pipe_mesh.position.z = 20;
  this.mesh.add(pipe_mesh);

  cap = new THREE.BoxGeometry(50, 5, 50);
  material = new THREE.MeshLambertMaterial({ color: 0x7d7d7d, map: pipeTexture });
  cap_mesh = new THREE.Mesh(cap, material);
  cap_mesh.castShadow = true;
  cap_mesh.receiveShadow = true;
  cap_mesh.position.y = 16;
  cap_mesh.position.x = 20;
  cap_mesh.position.z = 20;
  this.mesh.add(cap_mesh);

  const glassTexture = loader.load("textures/glass.jpg");
  glass = new THREE.BoxGeometry(2, 20, 20);
  window_material = new THREE.MeshStandardMaterial({ transparent: true, opacity: 0.4, map: glassTexture });
  window_material.roughness = 0.1;
  window_material.metalness = 0;
  window_material.reflectivty = 1;
  glass_mesh = new THREE.Mesh(glass, window_material);
  glass_mesh.position.z = 10;
  this.mesh.add(glass_mesh);

  glass = new THREE.BoxGeometry(2, 20, 20);
  glass_mesh = new THREE.Mesh(glass, window_material);
  glass_mesh.position.z = 10;
  glass_mesh.position.x = 40;
  this.mesh.add(glass_mesh);

  glass = new THREE.BoxGeometry(40, 20, 2);
  glass_mesh = new THREE.Mesh(glass, window_material);
  glass_mesh.position.x = 20;
  this.mesh.add(glass_mesh);
}

var stop;
function createStop() {
  stop = new Stop();
  scene.add(stop.mesh);
  stop.mesh.position.set(40, 15, 0);
}

var carMesh;
var Car = function () {
  var mtlLoader = new THREE.MTLLoader();

  mtlLoader.load('Models/Car.mtl', function (materials) {

    materials.preload();

    var objLoader = new THREE.OBJLoader();
    objLoader.setMaterials(materials);
    objLoader.load('Models/car.obj', function (object) {
      carMesh = object;
      carMesh.position.y = 0;
      carMesh.position.x = 450;
      carMesh.position.z = 100;
      carMesh.rotateY(-Math.PI/2);
      carMesh.scale.multiplyScalar(15);
      scene.add(carMesh);
      console.log("car")
    });

  });
}

function createCar() {
      car = new Car();
}
