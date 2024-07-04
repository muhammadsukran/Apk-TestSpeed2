const socket = io();

socket.on('screenData', (data) => {
    const img = new Image();
    img.src = `data:image/jpeg;base64,${data}`;
    document.getElementById('screen').appendChild(img);
});
