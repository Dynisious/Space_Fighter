package space_fighter_test_3d.gameWorld.physics.geometry;

import dynutils.SerialChain;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import softEngine3D.matrixes.FPoint3D;
import softEngine3D.objects.Triangle;
/**
 * <p>
 * A CollisionMesh is a collection of Triangles and other CollisionMeshes
 * forming a solid object. CollisionMeshes are able to calculate whether they
 * intersect with other CollisionMeshes.</p>
 *
 * @author Dynisious 19/10/2015
 * @version 0.1.1
 */
public class CollisionMesh implements SerialChain {
    private static CollisionMesh deserialiser; //A CollisionMesh meant only to deserialse CollisionMeshs.
    public static CollisionMesh getDeserialiser() {
        if (deserialiser == null)
            deserialiser = new CollisionMesh();
        return deserialiser;
    }
    private final FPoint3D location;
    public FPoint3D getLocation() {
        return location;
    }
    private final Triangle[] triangles;
    private final CollisionMesh[] meshs;
    private double maxCollisionDistance; //The furthest away a point could be
    //and still collide with this CollisionMesh.
    public double getMaxCollisionDistance() {
        return maxCollisionDistance;
    }

    /**
     *
     * @param location  The location of this CollisionMesh relative to it's
     *                  containing object.
     * @param triangles The Triangles making up this CollisionMesh.
     * @param meshs     The CollisionMeshes which are a part of this
     *                  CollisionMesh.
     */
    public CollisionMesh(final FPoint3D location, final Triangle[] triangles,
                         final CollisionMesh[] meshs) {
        this.location = location;
        this.triangles = triangles;
        this.meshs = meshs;
        final ArrayList<FPoint3D> vertexes = new ArrayList<>(triangles.length);
        evaluateValues(new ArrayList<>(triangles.length), vertexes);
        for (final FPoint3D vertex : vertexes)
            if (vertex.getMagnituid() > maxCollisionDistance)
                maxCollisionDistance = vertex.getMagnituid();
    }
    private CollisionMesh() {
        location = null;
        triangles = null;
        meshs = null;
    }

    /**
     * <p>
     * Checks for identical values in all Triangles and their vertexes to ensure
     * that minimal memory and processing is needed while using the
     * CollisionMesh.</p>
     *
     * @param ts           All used Triangles so far
     * @param usedVertexes All used vertexes so far.
     */
    private void evaluateValues(final ArrayList<Triangle> ts,
                                final ArrayList<FPoint3D> usedVertexes) {
        int index;
        for (int i = 0; i < triangles.length; i++) {
            if ((index = usedVertexes.indexOf(triangles[i].getP1())) == -1) {
                usedVertexes.add(triangles[i].getP1());
            } else {
                triangles[i].setP1(usedVertexes.get(index));
            }
            if ((index = usedVertexes.indexOf(triangles[i].getP2())) == -1) {
                usedVertexes.add(triangles[i].getP2());
            } else {
                triangles[i].setP2(usedVertexes.get(index));
            }
            if ((index = usedVertexes.indexOf(triangles[i].getP3())) == -1) {
                usedVertexes.add(triangles[i].getP3());
            } else {
                triangles[i].setP3(usedVertexes.get(index));
            }
            if ((index = ts.indexOf(triangles[i])) == -1) {
                ts.add(triangles[i]);
            } else {
                triangles[i] = ts.get(index);
            }
        }
        for (final CollisionMesh mesh : meshs) {
            mesh.evaluateValues(ts, usedVertexes);
        }
    }

    /**
     * <p>
     * Adds all vertexes to the passed HashSet. The vertexes are all relative to
     * the same origin.
     *
     * @param offset   The offset to apply to the meshes vertexes.
     * @param vertexes
     */
    private void getAllVertexes(FPoint3D offset,
                                final LinkedHashSet<FPoint3D> vertexes) {
        offset = offset.addition(location);
        for (final Triangle triangle : triangles) {
            vertexes.add(triangle.getP1().addition(offset));
            vertexes.add(triangle.getP2().addition(offset));
            vertexes.add(triangle.getP3().addition(offset));
        }
        for (final CollisionMesh mesh : meshs) {
            mesh.getAllVertexes(offset, vertexes);
        }
    }
    /**
     * <p>
     * Returns true if the passed CollisionMesh collides with this
     * CollisionMesh.</p>
     *
     * @param collisionMesh The collision mesh to check for collisions with.
     *
     * @return True if there is a collision.
     */
    public boolean checkCollision(final CollisionMesh collisionMesh) {
        final LinkedHashSet<FPoint3D> vertexes = new LinkedHashSet<>(
                triangles.length * 3, 1.5f);
        collisionMesh.getAllVertexes(new FPoint3D(-location.x, -location.y,
                -location.z), vertexes);
        return checkCollision(vertexes.toArray(new FPoint3D[vertexes.size()]),
                location);
    }

    /**
     * <p>
     * Returns true if one of the passed vertexes are inside this collision
     * mesh.
     *
     * @param vertexes The vertexes that may be colliding.
     * @param offset   The offset applied to each vertex during calculations to
     *                 make it relative to this CollisionMesh.
     *
     * @return True if there is a collision.
     */
    private boolean checkCollision(final FPoint3D[] vertexes,
                                   final FPoint3D offset) {
        double distance = Double.MAX_VALUE; //The closest distance observed between two points.
        Triangle closestTriangle = null;
        FPoint3D closestVertex = null;
        for (final Triangle triangle : triangles) {
            for (final FPoint3D vertex : vertexes) {
                if (Math.abs(triangle.getCenter().getMagnituid()
                        - vertex.subtraction(offset).getMagnituid()) < distance) {
                    distance = Math.abs(triangle.getCenter().getMagnituid()
                            - vertex.subtraction(offset).getMagnituid());
                    closestTriangle = triangle;
                    closestVertex = vertex.subtraction(offset);
                }
            }
        }
        distance = closestVertex.getMagnituid();
        for (final Triangle triangle : triangles) {
            if (triangle.getP1() == closestTriangle.getP1()
                    || triangle.getP1() == closestTriangle.getP2()
                    || triangle.getP1() == closestTriangle.getP3()
                    || triangle.getP2() == closestTriangle.getP1()
                    || triangle.getP2() == closestTriangle.getP2()
                    || triangle.getP2() == closestTriangle.getP3()
                    || triangle.getP3() == closestTriangle.getP1()
                    || triangle.getP3() == closestTriangle.getP2()
                    || triangle.getP3() == closestTriangle.getP3()) {
                final double p1Max = Math.max(
                        triangle.getP1().angleBetween(triangle.getP2()),
                        triangle.getP1().angleBetween(triangle.getP3()));
                final double p2Max = Math.max(
                        triangle.getP2().angleBetween(triangle.getP1()),
                        triangle.getP2().angleBetween(triangle.getP3()));
                final double p3Max = Math.max(
                        triangle.getP3().angleBetween(triangle.getP2()),
                        triangle.getP3().angleBetween(triangle.getP1()));
                if (distance < triangle.getCenter().getMagnituid()
                        && triangle.getP1().angleBetween(closestVertex) < p1Max
                        && triangle.getP2().angleBetween(closestVertex) < p2Max
                        && triangle.getP3().angleBetween(closestVertex) < p3Max)
                    return true;
            }
        }
        for (final CollisionMesh mesh : meshs) {
            if (mesh.checkCollision(vertexes, offset
                    .addition(mesh.getLocation())))
                return true;
        }
        return false;
    }

    /**
     * @return A deep copy of this CollisionMesh.
     */
    public CollisionMesh copy() {
        final CollisionMesh[] nMeshs = new CollisionMesh[meshs.length];
        for (int i = 0; i < meshs.length; i++) {
            nMeshs[i] = meshs[i].copy();
        }
        final Triangle[] nTriangles = new Triangle[triangles.length];
        for (int i = 0; i < triangles.length; i++) {
            nTriangles[i] = triangles[i].copy();
        }
        return new CollisionMesh(location.copy(), nTriangles, nMeshs);
    }

    private static final int pointSize = Double.BYTES + (Double.BYTES << 1); //The size in bytes of one triangle.
    @Override
    public int getSerialSize() {
        int size = (((triangles.length * 3) + 1) * pointSize)
                + (Integer.BYTES << 1) + Integer.BYTES + Double.BYTES; //2 ints
        //to head the arrays, a multitude of points for the Triangles, an int
        //for the point references size.
        for (final CollisionMesh mesh : meshs) //Get the serial length of each CollisionMesh.
            size += mesh.getSerialSize();
        return size;
    }

    @Override
    public void serialise(final ByteBuffer buffer)
            throws BufferOverflowException {
        buffer.putDouble(location.x).putDouble(location.y).putDouble(location.z)
                .putInt(triangles.length).putInt(meshs.length); //Put the primitives int first.
        final int[] referenceIDs = new int[triangles.length * 3]; //The ints will represent
        //the index of their reference to a FPoint3D in the array of FPoint3D's.
        final ArrayList<FPoint3D> pointReferences = new ArrayList<>(
                triangles.length);
        int index;
        for (int i = 0; i < triangles.length;) { //Extract each reference.
            if ((index = pointReferences.indexOf(triangles[i++].getP1())) == -1) {
                referenceIDs[i] = pointReferences.size();
                pointReferences.add(triangles[i].getP1());
            } else {
                referenceIDs[i] = index;
            }
            if ((index = pointReferences.indexOf(triangles[i++].getP2())) == -1) {
                referenceIDs[i] = pointReferences.size();
                pointReferences.add(triangles[i].getP2());
            } else {
                referenceIDs[i] = index;
            }
            if ((index = pointReferences.indexOf(triangles[i++].getP3())) == -1) {
                referenceIDs[i] = pointReferences.size();
                pointReferences.add(triangles[i].getP3());
            } else {
                referenceIDs[i] = index;
            }
        }
        buffer.putInt(pointReferences.size());
        for (final FPoint3D p : pointReferences)
            buffer.putDouble(p.x).putDouble(p.y).putDouble(p.z);
        for (final int id : referenceIDs)
            buffer.putInt(id);
        for (final CollisionMesh mesh : meshs)
            mesh.serialise(buffer);
    }

    @Override
    public <T extends SerialChain> T deserialise(final ByteBuffer buffer)
            throws BufferUnderflowException {
        final FPoint3D loc = new FPoint3D(); //The new location for the returned CollisionMesh.
        loc.x = buffer.getDouble();
        loc.y = buffer.getDouble();
        loc.z = buffer.getDouble();
        final Triangle[] ts = new Triangle[buffer.getInt()]; //The new array of Triangles.
        final CollisionMesh[] cms = new CollisionMesh[buffer.getInt()]; //The new array of CollisionMeshs.
        final FPoint3D[] pointReferences = new FPoint3D[buffer.getInt()]; //The FPoint3D references to use.
        final int[] referenceIDs = new int[ts.length * 3]; //The ints represent
        //the index of their reference to a FPoint3D in the array of FPoint3D's.
        for (int i = 0; i < pointReferences.length; i++) { //Get all the FPoint3D's
            pointReferences[i] = new FPoint3D(buffer.getDouble(),
                    buffer.getDouble(), buffer.getDouble());
        }
        for (int i = 0; i < referenceIDs.length; i++) //Get all the reference ID's
            referenceIDs[i] = buffer.getInt();
        for (int i = 0, point = 0; i < ts.length; i++) //Get all the Triangles.
            ts[i] = new Triangle(pointReferences[referenceIDs[point++]],
                    pointReferences[referenceIDs[point++]],
                    pointReferences[referenceIDs[point++]], true);
        for (int i = 0; i < cms.length; i++) //Get all the CollisionMeshs.
            cms[i] = deserialise(buffer);
        return (T) new CollisionMesh(loc, ts, cms); //Return the new CollisionMesh.
    }

}
