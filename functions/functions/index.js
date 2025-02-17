const { onValueDeleted } = require("firebase-functions/v2/database");
const { initializeApp } = require("firebase-admin/app");
const { getAuth } = require("firebase-admin/auth");

initializeApp();

exports.deleteUserOnDatabaseRemoval = onValueDeleted("/Usuarios/{uid}", async (event) => {
  const uid = event.params.uid;

  try {
    await getAuth().deleteUser(uid);
    console.log(`Usuario ${uid} eliminado de Firebase Authentication`);
  } catch (error) {
    console.error(`Error eliminando usuario ${uid}:`, error);
  }
});
